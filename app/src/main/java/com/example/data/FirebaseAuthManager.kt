package com.example.data

import android.content.Context
import android.content.SharedPreferences
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class FirebaseAuthManager(
    private val context: Context,
    private val client: OkHttpClient
) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("nafitv24_firebase_auth", Context.MODE_PRIVATE)

    companion object {
        const val PRIMARY_ADMIN_EMAIL = "nafitv24@gmail.com"
        const val MASTER_ADMIN_PASS = "NMD646542"
        const val DEFAULT_ADMIN_PIN = "40541273"
        private const val API_KEY = MediaRepository.FIREBASE_API_KEY
        private const val LEGACY_API_KEY = MediaRepository.LEGACY_FIREBASE_API_KEY

        private const val KEY_ID_TOKEN = "auth_id_token"
        private const val KEY_REFRESH_TOKEN = "auth_refresh_token"
        private const val KEY_EMAIL = "auth_email"
        private const val KEY_UID = "auth_uid"
        private const val KEY_EXPIRY_TIMESTAMP = "auth_expiry_timestamp"
        private const val KEY_AUTH_SOURCE = "auth_source"

        private const val KEY_LEGACY_ID_TOKEN = "legacy_auth_id_token"
        private const val KEY_LEGACY_REFRESH_TOKEN = "legacy_auth_refresh_token"
        private const val KEY_LEGACY_EXPIRY_TIMESTAMP = "legacy_auth_expiry_timestamp"
    }

    private fun isFakeLocalToken(token: String?): Boolean {
        return token.isNullOrBlank() ||
            token.startsWith("admin_master_") ||
            token.startsWith("local_admin_") ||
            !token.contains(".")
    }

    fun isUserLoggedIn(): Boolean {
        val token = prefs.getString(KEY_ID_TOKEN, null)
        val legacyToken = prefs.getString(KEY_LEGACY_ID_TOKEN, null)
        val email = prefs.getString(KEY_EMAIL, null)
        return (!token.isNullOrBlank() || !legacyToken.isNullOrBlank()) && !email.isNullOrBlank()
    }

    fun hasCloudAuthToken(): Boolean {
        val primary = prefs.getString(KEY_ID_TOKEN, null)
        val legacy = prefs.getString(KEY_LEGACY_ID_TOKEN, null)
        return !isFakeLocalToken(primary) || !isFakeLocalToken(legacy)
    }

    fun isAuthorizedAdmin(): Boolean {
        if (!isUserLoggedIn()) return false
        val email = getLoggedInEmail() ?: return false
        return email.trim().equals(PRIMARY_ADMIN_EMAIL, ignoreCase = true) ||
                email.trim().endsWith("@gmail.com", ignoreCase = true)
    }

    fun getLoggedInEmail(): String? {
        return prefs.getString(KEY_EMAIL, null)
    }

    fun getLoggedInUid(): String? {
        return prefs.getString(KEY_UID, null)
    }

    fun getStoredIdToken(): String? {
        return prefs.getString(KEY_ID_TOKEN, null)
    }

    fun getStoredRefreshToken(): String? {
        return prefs.getString(KEY_REFRESH_TOKEN, null)
    }

    suspend fun getValidIdToken(forceRefresh: Boolean = false): String? = withContext(Dispatchers.IO) {
        val currentToken = prefs.getString(KEY_ID_TOKEN, null)
        if (isFakeLocalToken(currentToken)) {
            // Try active Firebase SDK user if signed in with Google
            val sdkToken = tryRefreshFromFirebaseSdk(forceRefresh = true)
            if (!isFakeLocalToken(sdkToken)) return@withContext sdkToken
            return@withContext null
        }

        val expiry = prefs.getLong(KEY_EXPIRY_TIMESTAMP, 0L)
        val now = System.currentTimeMillis()

        if (forceRefresh || now >= expiry - 300_000L) {
            val sdkToken = tryRefreshFromFirebaseSdk(forceRefresh = true)
            if (!isFakeLocalToken(sdkToken)) {
                return@withContext sdkToken
            }
            val refreshed = refreshAuthToken()
            if (!isFakeLocalToken(refreshed)) {
                return@withContext refreshed
            }
            // If token is already expired and refresh failed, do not return stale JWT
            if (now >= expiry) {
                return@withContext null
            }
        }
        currentToken
    }

    suspend fun getValidLegacyIdToken(forceRefresh: Boolean = false): String? = withContext(Dispatchers.IO) {
        val currentToken = prefs.getString(KEY_LEGACY_ID_TOKEN, null)
        if (isFakeLocalToken(currentToken)) {
            return@withContext null
        }
        val expiry = prefs.getLong(KEY_LEGACY_EXPIRY_TIMESTAMP, 0L)
        val now = System.currentTimeMillis()
        if (forceRefresh || now >= expiry - 300_000L) {
            val refreshed = refreshLegacyAuthToken()
            if (!isFakeLocalToken(refreshed)) {
                return@withContext refreshed
            }
            if (now >= expiry) {
                return@withContext null
            }
        }
        currentToken
    }

    suspend fun getRtdbTokenForUrl(rtdbUrl: String, forceRefresh: Boolean = false): String? {
        val lower = rtdbUrl.lowercase()
        return when {
            lower.contains(MediaRepository.LEGACY_FIREBASE_PROJECT_ID.lowercase()) -> {
                getValidLegacyIdToken(forceRefresh)
            }
            lower.contains(MediaRepository.FIREBASE_PROJECT_ID.lowercase()) -> {
                getValidIdToken(forceRefresh)
            }
            else -> {
                getValidIdToken(forceRefresh) ?: getValidLegacyIdToken(forceRefresh)
            }
        }
    }

    private suspend fun tryRefreshFromFirebaseSdk(forceRefresh: Boolean): String? {
        return try {
            val user = Firebase.auth.currentUser ?: return null
            val tokenResult = user.getIdToken(forceRefresh).await()
            val token = tokenResult?.token
            if (!isFakeLocalToken(token)) {
                val email = user.email ?: prefs.getString(KEY_EMAIL, "") ?: ""
                val expiry = System.currentTimeMillis() + 3300_000L
                saveSession(
                    idToken = token!!,
                    refreshToken = prefs.getString(KEY_REFRESH_TOKEN, "") ?: "",
                    email = email,
                    uid = user.uid,
                    expiryTime = expiry,
                    authSource = "google_firebase"
                )
                token
            } else {
                null
            }
        } catch (_: Throwable) {
            null
        }
    }

    private data class AuthTokenResponse(
        val idToken: String,
        val refreshToken: String,
        val email: String,
        val uid: String,
        val expiryTime: Long
    )

    private fun executeSignInWithPassword(
        apiKey: String,
        email: String,
        password: String
    ): Pair<AuthTokenResponse?, String> {
        return try {
            val url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key=$apiKey"
            val payload = JSONObject().apply {
                put("email", email)
                put("password", password)
                put("returnSecureToken", true)
            }
            val body = payload.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                val resBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    val json = JSONObject(resBody)
                    val idToken = json.optString("idToken")
                    val refreshToken = json.optString("refreshToken")
                    val userEmail = json.optString("email", email)
                    val uid = json.optString("localId")
                    val expiresInSec = json.optString("expiresIn", "3600").toLongOrNull() ?: 3600L
                    val expiryTime = System.currentTimeMillis() + (expiresInSec * 1000L)
                    Pair(AuthTokenResponse(idToken, refreshToken, userEmail, uid, expiryTime), "")
                } else {
                    Pair(null, resBody)
                }
            }
        } catch (e: Exception) {
            Pair(null, e.localizedMessage ?: "NETWORK_ERROR")
        }
    }

    private fun executeSignUpWithPassword(
        apiKey: String,
        email: String,
        password: String
    ): Pair<AuthTokenResponse?, String> {
        return try {
            val url = "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key=$apiKey"
            val payload = JSONObject().apply {
                put("email", email)
                put("password", password)
                put("returnSecureToken", true)
            }
            val body = payload.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                val resBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    val json = JSONObject(resBody)
                    val idToken = json.optString("idToken")
                    val refreshToken = json.optString("refreshToken")
                    val userEmail = json.optString("email", email)
                    val uid = json.optString("localId")
                    val expiresInSec = json.optString("expiresIn", "3600").toLongOrNull() ?: 3600L
                    val expiryTime = System.currentTimeMillis() + (expiresInSec * 1000L)
                    Pair(AuthTokenResponse(idToken, refreshToken, userEmail, uid, expiryTime), "")
                } else {
                    Pair(null, resBody)
                }
            }
        } catch (e: Exception) {
            Pair(null, e.localizedMessage ?: "NETWORK_ERROR")
        }
    }

    suspend fun signInWithEmail(email: String, pass: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val cleanEmail = email.trim()
        val cleanPass = pass.trim()

        if (cleanEmail.isBlank() || cleanPass.isBlank()) {
            return@withContext Pair(false, "ইমেইল এবং পাসওয়ার্ড উভয়ই প্রদান করুন")
        }

        val isMasterAdminPass = cleanPass.equals(MASTER_ADMIN_PASS, ignoreCase = true) ||
                cleanPass == DEFAULT_ADMIN_PIN ||
                cleanPass == MediaRepository.DEFAULT_ADMIN_PIN

        val isMasterAdminEmail = cleanEmail.equals(PRIMARY_ADMIN_EMAIL, ignoreCase = true) ||
                cleanEmail.endsWith("@gmail.com", ignoreCase = true)

        // Always attempt real Firebase Auth on BOTH primary project and legacy RTDB project first
        val (primaryRes, primaryErr) = executeSignInWithPassword(API_KEY, cleanEmail, cleanPass)
        val (legacyRes, legacyErr) = executeSignInWithPassword(LEGACY_API_KEY, cleanEmail, cleanPass)

        if (primaryRes != null || legacyRes != null) {
            val activeEmail = primaryRes?.email ?: legacyRes?.email ?: cleanEmail
            val activeUid = primaryRes?.uid ?: legacyRes?.uid ?: "admin_${cleanEmail.replace("@", "_").replace(".", "_")}"

            if (primaryRes != null) {
                saveSession(
                    idToken = primaryRes.idToken,
                    refreshToken = primaryRes.refreshToken,
                    email = activeEmail,
                    uid = activeUid,
                    expiryTime = primaryRes.expiryTime,
                    authSource = "firebase_email"
                )
            } else {
                // Keep non-fake email/uid context while attempting sign-up on primary if account existed only on legacy
                val (createdPrimary, _) = executeSignUpWithPassword(API_KEY, cleanEmail, cleanPass)
                if (createdPrimary != null) {
                    saveSession(
                        idToken = createdPrimary.idToken,
                        refreshToken = createdPrimary.refreshToken,
                        email = activeEmail,
                        uid = createdPrimary.uid,
                        expiryTime = createdPrimary.expiryTime,
                        authSource = "firebase_email"
                    )
                } else {
                    saveSession(
                        idToken = "",
                        refreshToken = "",
                        email = activeEmail,
                        uid = activeUid,
                        expiryTime = 0L,
                        authSource = "firebase_email"
                    )
                }
            }

            if (legacyRes != null) {
                saveLegacySession(legacyRes.idToken, legacyRes.refreshToken, legacyRes.expiryTime)
            }

            return@withContext Pair(true, "✅ ফায়ারবেস ক্লাউড অথেন্টিকেশন সফল হয়েছে! স্বাগতম $activeEmail")
        }

        // Local Master Admin fallback only if Firebase Identity Toolkit rejected or wasn't configured
        if (isMasterAdminPass && isMasterAdminEmail) {
            val expiryTime = System.currentTimeMillis() + (365L * 24 * 3600 * 1000L)
            saveSession(
                idToken = "local_admin_session_${System.currentTimeMillis()}",
                refreshToken = "",
                email = cleanEmail,
                uid = "admin_${cleanEmail.replace("@", "_").replace(".", "_")}",
                expiryTime = expiryTime,
                authSource = "local_pin"
            )
            saveLegacySession("", "", 0L)
            return@withContext Pair(
                true,
                "⚠️ লোকাল অ্যাডমিন সেশন চালু হয়েছে। ফায়ারবেস রুলস সুরক্ষিত থাকলে Google Sign-In অথবা আসল ফায়ারবেস পাসওয়ার্ড দিয়ে লগইন করুন।"
            )
        }

        val errBody = primaryErr.ifBlank { legacyErr }
        if (errBody.startsWith("{")) {
            Pair(false, parseAuthError(errBody))
        } else {
            Pair(false, "নেটওয়ার্ক ত্রুটি: $errBody")
        }
    }

    suspend fun signUpWithEmail(email: String, pass: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val cleanEmail = email.trim()
        val cleanPass = pass.trim()

        if (cleanEmail.isBlank() || cleanPass.length < 6) {
            return@withContext Pair(false, "পাসওয়ার্ড অন্তত ৬ অক্ষরের হতে হবে")
        }

        val isMasterAdminPass = cleanPass.equals(MASTER_ADMIN_PASS, ignoreCase = true) ||
                cleanPass == DEFAULT_ADMIN_PIN ||
                cleanPass == MediaRepository.DEFAULT_ADMIN_PIN

        val isMasterAdminEmail = cleanEmail.equals(PRIMARY_ADMIN_EMAIL, ignoreCase = true) ||
                cleanEmail.endsWith("@gmail.com", ignoreCase = true)

        val (primaryRes, primaryErr) = executeSignUpWithPassword(API_KEY, cleanEmail, cleanPass)
        val (legacyRes, _) = executeSignUpWithPassword(LEGACY_API_KEY, cleanEmail, cleanPass)

        if (primaryRes != null || legacyRes != null) {
            val activeEmail = primaryRes?.email ?: legacyRes?.email ?: cleanEmail
            val activeUid = primaryRes?.uid ?: legacyRes?.uid ?: "admin_${cleanEmail.replace("@", "_").replace(".", "_")}"
            if (primaryRes != null) {
                saveSession(primaryRes.idToken, primaryRes.refreshToken, activeEmail, activeUid, primaryRes.expiryTime, "firebase_email")
            } else {
                saveSession("", "", activeEmail, activeUid, 0L, "firebase_email")
            }
            if (legacyRes != null) {
                saveLegacySession(legacyRes.idToken, legacyRes.refreshToken, legacyRes.expiryTime)
            }
            return@withContext Pair(true, "✅ ফায়ারবেস অ্যাকাউন্ট তৈরি ও লগইন সম্পন্ন হয়েছে! স্বাগতম $activeEmail")
        }

        if (isMasterAdminPass && isMasterAdminEmail && (primaryErr.contains("OPERATION_NOT_ALLOWED") || primaryErr.contains("EMAIL_EXISTS"))) {
            return@withContext signInWithEmail(cleanEmail, cleanPass)
        }

        val errorMsg = if (primaryErr.startsWith("{")) parseAuthError(primaryErr) else "রেজিস্ট্রেশন ত্রুটি: $primaryErr"
        Pair(false, errorMsg)
    }

    suspend fun sendPasswordReset(email: String): Pair<Boolean, String> = withContext(Dispatchers.IO) {
        val cleanEmail = email.trim()
        if (cleanEmail.isBlank()) {
            return@withContext Pair(false, "ইমেইল প্রদান করুন")
        }

        try {
            val url = "https://identitytoolkit.googleapis.com/v1/accounts:sendOobCode?key=$API_KEY"
            val payload = JSONObject().apply {
                put("requestType", "PASSWORD_RESET")
                put("email", cleanEmail)
            }
            val body = payload.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build()

            client.newCall(request).execute().use { response ->
                val resBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    Pair(true, "$cleanEmail ঠিকানায় পাসওয়ার্ড রিসেট লিংক পাঠানো হয়েছে। ইমেইল চেক করুন।")
                } else {
                    val errorMsg = parseAuthError(resBody)
                    Pair(false, errorMsg)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Pair(false, "পাসওয়ার্ড রিসেট ত্রুটি: ${e.localizedMessage ?: "ব্যর্থ হয়েছে"}")
        }
    }

    suspend fun refreshAuthToken(): String? = withContext(Dispatchers.IO) {
        val refreshToken = prefs.getString(KEY_REFRESH_TOKEN, null)
        if (refreshToken.isNullOrBlank()) return@withContext null
        try {
            val url = "https://securetoken.googleapis.com/v1/token?key=$API_KEY"
            val formBody = "grant_type=refresh_token&refresh_token=$refreshToken"
            val body = formBody.toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .build()

            client.newCall(request).execute().use { response ->
                val resBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    val json = JSONObject(resBody)
                    val newIdToken = json.optString("id_token")
                    val newRefreshToken = json.optString("refresh_token", refreshToken)
                    val userUid = json.optString("user_id", prefs.getString(KEY_UID, "") ?: "")
                    val expiresInSec = json.optString("expires_in", "3600").toLongOrNull() ?: 3600L
                    val expiryTime = System.currentTimeMillis() + (expiresInSec * 1000L)

                    saveSession(newIdToken, newRefreshToken, prefs.getString(KEY_EMAIL, "") ?: "", userUid, expiryTime, prefs.getString(KEY_AUTH_SOURCE, "firebase_email") ?: "firebase_email")
                    return@withContext newIdToken
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }

    private suspend fun refreshLegacyAuthToken(): String? = withContext(Dispatchers.IO) {
        val refreshToken = prefs.getString(KEY_LEGACY_REFRESH_TOKEN, null)
        if (refreshToken.isNullOrBlank()) return@withContext null
        try {
            val url = "https://securetoken.googleapis.com/v1/token?key=$LEGACY_API_KEY"
            val formBody = "grant_type=refresh_token&refresh_token=$refreshToken"
            val body = formBody.toRequestBody("application/x-www-form-urlencoded".toMediaTypeOrNull())
            val request = Request.Builder().url(url).post(body).build()
            client.newCall(request).execute().use { response ->
                val resBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    val json = JSONObject(resBody)
                    val newIdToken = json.optString("id_token")
                    val newRefreshToken = json.optString("refresh_token", refreshToken)
                    val expiresInSec = json.optString("expires_in", "3600").toLongOrNull() ?: 3600L
                    val expiryTime = System.currentTimeMillis() + (expiresInSec * 1000L)
                    saveLegacySession(newIdToken, newRefreshToken, expiryTime)
                    return@withContext newIdToken
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        null
    }

    suspend fun exchangeGoogleTokenForLegacyProject(googleIdToken: String): Boolean = withContext(Dispatchers.IO) {
        if (googleIdToken.isBlank()) return@withContext false
        try {
            val url = "https://identitytoolkit.googleapis.com/v1/accounts:signInWithIdp?key=$LEGACY_API_KEY"
            val payload = JSONObject().apply {
                put("postBody", "id_token=$googleIdToken&providerId=google.com")
                put("requestUri", "http://localhost")
                put("returnIdpCredential", true)
                put("returnSecureToken", true)
            }
            val body = payload.toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
            val request = Request.Builder()
                .url(url)
                .post(body)
                .header("Content-Type", "application/json")
                .build()
            client.newCall(request).execute().use { response ->
                val resBody = response.body?.string() ?: ""
                if (response.isSuccessful) {
                    val json = JSONObject(resBody)
                    val idToken = json.optString("idToken")
                    val refreshToken = json.optString("refreshToken")
                    val expiresInSec = json.optString("expiresIn", "3600").toLongOrNull() ?: 3600L
                    val expiryTime = System.currentTimeMillis() + (expiresInSec * 1000L)
                    if (!isFakeLocalToken(idToken)) {
                        saveLegacySession(idToken, refreshToken, expiryTime)
                        return@withContext true
                    }
                }
            }
        } catch (_: Exception) {}
        false
    }

    fun saveGoogleSession(idToken: String, email: String, uid: String) {
        saveSession(idToken, "", email, uid, System.currentTimeMillis() + 3300_000L, "google_firebase")
    }

    fun signOut() {
        try {
            Firebase.auth.signOut()
        } catch (_: Throwable) {}
        prefs.edit().clear().apply()
    }

    private fun saveSession(
        idToken: String,
        refreshToken: String,
        email: String,
        uid: String,
        expiryTime: Long,
        authSource: String = "firebase_email"
    ) {
        prefs.edit()
            .putString(KEY_ID_TOKEN, idToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .putString(KEY_EMAIL, email)
            .putString(KEY_UID, uid)
            .putLong(KEY_EXPIRY_TIMESTAMP, expiryTime)
            .putString(KEY_AUTH_SOURCE, authSource)
            .apply()
    }

    private fun saveLegacySession(
        idToken: String,
        refreshToken: String,
        expiryTime: Long
    ) {
        prefs.edit()
            .putString(KEY_LEGACY_ID_TOKEN, idToken)
            .putString(KEY_LEGACY_REFRESH_TOKEN, refreshToken)
            .putLong(KEY_LEGACY_EXPIRY_TIMESTAMP, expiryTime)
            .apply()
    }

    private fun parseAuthError(rawResponse: String): String {
        return try {
            val json = JSONObject(rawResponse)
            val errorObj = json.optJSONObject("error")
            val code = errorObj?.optString("message") ?: "UNKNOWN_ERROR"

            when {
                code.contains("EMAIL_NOT_FOUND") -> "এই ইমেইল দিয়ে কোনো অ্যাকাউন্ট পাওয়া যায়নি। অনুগ্রহ করে সাইন আপ করুন অথবা Google Sign-In ব্যবহার করুন।"
                code.contains("INVALID_PASSWORD") || code.contains("INVALID_LOGIN_CREDENTIALS") -> "ভুল পাসওয়ার্ড অথবা অবৈধ তথ্য।"
                code.contains("USER_DISABLED") -> "এই অ্যাকাউন্টটি ফায়ারবেস দ্বারা নিষ্ক্রিয় করা হয়েছে।"
                code.contains("EMAIL_EXISTS") -> "এই ইমেইল দিয়ে ইতোমধ্যে একটি অ্যাকাউন্ট রয়েছে। লগইন করুন।"
                code.contains("TOO_MANY_ATTEMPTS_TRY_LATER") -> "অনেকবার চেষ্টা করা হয়েছে। কিছুক্ষণ পর আবার চেষ্টা করুন।"
                code.contains("WEAK_PASSWORD") -> "পাসওয়ার্ডটি দুর্বল। অন্তত ৬ অক্ষরের শক্তিশালী পাসওয়ার্ড দিন।"
                code.contains("PASSWORD_LOGIN_DISABLED") -> "ফায়ারবেসে পাসওয়ার্ড লগইন নিষ্ক্রিয়। অনুগ্রহ করে Google দিয়ে সাইন ইন করুন।"
                code.contains("OPERATION_NOT_ALLOWED") -> "ফায়ারবেস কনসোলে Email/Password Auth চালু করা নেই। অনুগ্রহ করে Google দিয়ে সাইন ইন করুন।"
                else -> "অথেন্টিকেশন ব্যর্থ: $code"
            }
        } catch (e: Exception) {
            "ত্রুটি: $rawResponse"
        }
    }
}
