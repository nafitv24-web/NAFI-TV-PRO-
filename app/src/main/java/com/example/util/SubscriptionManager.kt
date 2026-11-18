package com.example.util

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.random.Random

data class UserSubscriptionRecord(
    val phone: String,
    val subscriptionStatus: String = "inactive",
    val expiryDate: String = "",
    val packageType: String = "",
    val voucherCode: String = ""
)

sealed class SubscriptionCheckResult {
    data object Checking : SubscriptionCheckResult()
    data object Disabled : SubscriptionCheckResult()
    data object NeedsPhoneOrVoucher : SubscriptionCheckResult()
    data class Active(
        val record: UserSubscriptionRecord,
        val remainingDays: Long
    ) : SubscriptionCheckResult()

    data class ExpiredOrInactive(
        val record: UserSubscriptionRecord?,
        val reason: String
    ) : SubscriptionCheckResult()

    data class NetworkError(
        val message: String,
        val cachedActive: Active? = null
    ) : SubscriptionCheckResult()
}

/**
 * Firebase Realtime Database subscription & voucher validator for /users/{user_phone}/.
 * Validates `subscription_status == "active"` and compares `expiry_date` (YYYY-MM-DD)
 * against the current device date.
 */
object SubscriptionManager {

    private const val DATE_PATTERN = "yyyy-MM-dd"

    fun normalizePhone(raw: String): String {
        val cleaned = raw.trim()
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
            .replace(".", "")
            .replace("#", "")
            .replace("$", "")
            .replace("[", "")
            .replace("]", "")
            .replace("/", "")

        return when {
            cleaned.startsWith("+880") -> "0" + cleaned.removePrefix("+880")
            cleaned.startsWith("880") && cleaned.length >= 13 -> "0" + cleaned.removePrefix("880")
            else -> cleaned
        }
    }

    /**
     * Checks whether `expiry_date` (format YYYY-MM-DD) is today or in the future.
     * Returns true if today <= expiryDate, false if current device date > expiryDate or invalid.
     */
    fun isDateValid(expiryDateStr: String?, referenceDate: Date = Date()): Boolean {
        val expiry = parseDateStrict(expiryDateStr) ?: return false
        val today = normalizeToStartOfDay(referenceDate)
        return !today.after(expiry)
    }

    /**
     * Returns remaining days (>= 0 if valid through today or later; negative if already expired).
     */
    fun remainingDays(expiryDateStr: String?, referenceDate: Date = Date()): Long {
        val expiry = parseDateStrict(expiryDateStr) ?: return -1L
        val today = normalizeToStartOfDay(referenceDate)
        val diffMs = expiry.time - today.time
        return diffMs / (24L * 60L * 60L * 1000L)
    }

    fun addMonthsToDateString(
        months: Int,
        baseDateStr: String? = null,
        referenceDate: Date = Date()
    ): String {
        val today = normalizeToStartOfDay(referenceDate)
        val parsedBase = parseDateStrict(baseDateStr)
        val startDate = if (parsedBase != null && !parsedBase.before(today)) parsedBase else today
        val cal = Calendar.getInstance(Locale.US).apply {
            time = startDate
            add(Calendar.MONTH, months.coerceAtLeast(1))
        }
        return formatDate(cal.time)
    }

    fun addDaysToDateString(
        days: Int,
        baseDateStr: String? = null,
        referenceDate: Date = Date()
    ): String {
        val today = normalizeToStartOfDay(referenceDate)
        val parsedBase = parseDateStrict(baseDateStr)
        val startDate = if (parsedBase != null && !parsedBase.before(today)) parsedBase else today
        val cal = Calendar.getInstance(Locale.US).apply {
            time = startDate
            add(Calendar.DAY_OF_YEAR, days.coerceAtLeast(1))
        }
        return formatDate(cal.time)
    }

    fun formatDate(date: Date): String {
        val sdf = SimpleDateFormat(DATE_PATTERN, Locale.US)
        return sdf.format(date)
    }

    fun generateVoucherCode(digits: Int = 4): String {
        val min = when (digits) {
            6 -> 100000
            else -> 1000
        }
        val max = when (digits) {
            6 -> 999999
            else -> 9999
        }
        return Random.nextInt(min, max + 1).toString()
    }

    fun parseDateStrict(dateStr: String?): Date? {
        val trimmed = dateStr?.trim().orEmpty()
        if (trimmed.isBlank()) return null
        return try {
            val sdf = SimpleDateFormat(DATE_PATTERN, Locale.US).apply {
                isLenient = false
            }
            val parsed = sdf.parse(trimmed) ?: return null
            normalizeToStartOfDay(parsed)
        } catch (_: Exception) {
            null
        }
    }

    private fun normalizeToStartOfDay(date: Date): Date {
        val cal = Calendar.getInstance(Locale.US).apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return cal.time
    }

    fun parseUserJson(phone: String, json: JSONObject): UserSubscriptionRecord {
        return UserSubscriptionRecord(
            phone = normalizePhone(phone),
            subscriptionStatus = json.optString("subscription_status", "").trim(),
            expiryDate = json.optString("expiry_date", "").trim(),
            packageType = json.optString("package_type", "").trim(),
            voucherCode = json.optString("voucher_code", "").trim()
        )
    }

    fun toUserJson(record: UserSubscriptionRecord): JSONObject {
        return JSONObject().apply {
            put("subscription_status", record.subscriptionStatus.ifBlank { "active" })
            put("expiry_date", record.expiryDate)
            put("package_type", record.packageType.ifBlank { "1Month" })
            put("voucher_code", record.voucherCode)
        }
    }

    fun evaluateRecord(
        record: UserSubscriptionRecord?,
        referenceDate: Date = Date()
    ): SubscriptionCheckResult {
        if (record == null) {
            return SubscriptionCheckResult.ExpiredOrInactive(
                record = null,
                reason = "এই ফোন নম্বরের কোনো সাবস্ক্রিপশন তথ্য পাওয়া যায়নি।"
            )
        }
        if (!record.subscriptionStatus.equals("active", ignoreCase = true)) {
            val statusLabel = record.subscriptionStatus.ifBlank { "inactive" }
            return SubscriptionCheckResult.ExpiredOrInactive(
                record = record,
                reason = "আপনার সাবস্ক্রিপশন স্ট্যাটাস বর্তমানে নিষ্ক্রিয় ($statusLabel) রয়েছে।"
            )
        }
        if (!isDateValid(record.expiryDate, referenceDate)) {
            val dateLabel = record.expiryDate.ifBlank { "নির্ধারিত নয়" }
            return SubscriptionCheckResult.ExpiredOrInactive(
                record = record,
                reason = "আপনার প্যাকেজের মেয়াদ ($dateLabel) শেষ হয়ে গেছে।"
            )
        }
        return SubscriptionCheckResult.Active(
            record = record,
            remainingDays = remainingDays(record.expiryDate, referenceDate)
        )
    }
}
