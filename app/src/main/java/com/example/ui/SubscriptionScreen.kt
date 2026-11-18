package com.example.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteSweep
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.PhoneAndroid
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.MediaRepository
import com.example.util.SubscriptionCheckResult
import com.example.util.SubscriptionManager
import com.example.util.UserSubscriptionRecord
import kotlinx.coroutines.launch

/**
 * AlertDialog shown immediately when Firebase reports subscription expired (`today > expiry_date`)
 * or `subscription_status != "active"`.
 */
@Composable
fun SubscriptionExpiredAlertDialog(
    phone: String,
    reason: String,
    packageType: String?,
    expiryDate: String?,
    onGoToVoucherEntry: () -> Unit,
    onRetryCheck: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { /* Mandatory gate: must renew/verify or retry */ },
        containerColor = Color(0xFF0F172A),
        titleContentColor = Color.White,
        textContentColor = Color(0xFFCBD5E1),
        icon = {
            Surface(
                shape = CircleShape,
                color = Color(0xFFEF4444).copy(alpha = 0.16f),
                modifier = Modifier.size(52.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Rounded.WarningAmber,
                        contentDescription = "Subscription Expired",
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(28.dp)
                    )
                }
            }
        },
        title = {
            Text(
                text = "সাবস্ক্রিপশন মেয়াদোত্তীর্ণ / নিষ্ক্রিয়",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = reason,
                    color = Color(0xFFFCA5A5),
                    fontSize = 13.5.sp,
                    lineHeight = 19.sp
                )
                if (phone.isNotBlank() || !expiryDate.isNullOrBlank()) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFF1E293B),
                        border = BorderStroke(1.dp, Color(0xFF334155)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            if (phone.isNotBlank()) {
                                Text("ফোন নম্বর: $phone", color = Color.White, fontSize = 12.5.sp, fontWeight = FontWeight.SemiBold)
                            }
                            if (!packageType.isNullOrBlank()) {
                                Text("প্যাকেজ: $packageType", color = Color(0xFF94A3B8), fontSize = 12.sp)
                            }
                            if (!expiryDate.isNullOrBlank()) {
                                Text("মেয়াদ শেষ: $expiryDate", color = Color(0xFFF59E0B), fontSize = 12.sp, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
                Text(
                    text = "অ্যাপ ব্যবহার করতে অনুগ্রহ করে নতুন ভাউচার কোড দিয়ে সাবস্ক্রিপশন রিনিউ করুন।",
                    color = Color(0xFF94A3B8),
                    fontSize = 12.sp,
                    lineHeight = 17.sp
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onGoToVoucherEntry,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF00E5FF),
                    contentColor = Color.Black
                ),
                modifier = Modifier
                    .defaultMinSize(minHeight = 48.dp)
                    .testTag("expired_dialog_voucher_button")
            ) {
                Text("ভাউচার কোড দিন", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = onRetryCheck,
                border = BorderStroke(1.dp, Color(0xFF334155)),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                modifier = Modifier
                    .defaultMinSize(minHeight = 48.dp)
                    .testTag("expired_dialog_retry_button")
            ) {
                Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("আবার চেক করুন")
            }
        }
    )
}

/**
 * Full-screen Subscription & Voucher Entry Gate shown when user needs to activate or renew.
 */
@Composable
fun SubscriptionVoucherGateScreen(
    repository: MediaRepository,
    initialPhone: String,
    statusMessage: String?,
    isChecking: Boolean,
    supportContact: String,
    onActivated: (SubscriptionCheckResult.Active) -> Unit,
    onRetryStartupCheck: () -> Unit,
    onOpenAdminLogin: () -> Unit
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var phoneInput by remember(initialPhone) { mutableStateOf(initialPhone) }
    var voucherInput by remember { mutableStateOf("") }
    var isSubmitting by remember { mutableStateOf(false) }
    var inlineError by remember(statusMessage) { mutableStateOf(statusMessage) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF020617), Color(0xFF0B1120), Color(0xFF020617))
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = 480.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Branding Header
            Surface(
                shape = RoundedCornerShape(22.dp),
                color = Color(0xFF0F172A),
                border = BorderStroke(1.5.dp, Color(0xFF00E5FF).copy(alpha = 0.5f)),
                modifier = Modifier.size(76.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = "NAFI TV Pro Logo",
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Fit
                    )
                }
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "NAFI TV Pro সাবস্ক্রিপশন",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "চালিয়ে যেতে আপনার রেজিস্টার্ড ফোন নম্বর ও ভাউচার কোড দিয়ে যাচাই করুন",
                    color = Color(0xFF94A3B8),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 18.sp
                )
            }

            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                border = BorderStroke(1.dp, Color(0xFF1E293B)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    if (isChecking) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            CircularProgressIndicator(
                                color = Color(0xFF00E5FF),
                                strokeWidth = 2.5.dp,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "Firebase থেকে সাবস্ক্রিপশন মেয়াদ যাচাই হচ্ছে...",
                                color = Color(0xFF00E5FF),
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    if (!inlineError.isNullOrBlank()) {
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFEF4444).copy(alpha = 0.14f),
                            border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f)),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.WarningAmber,
                                    contentDescription = "Status Warning",
                                    tint = Color(0xFFFCA5A5),
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = inlineError.orEmpty(),
                                    color = Color(0xFFFECACA),
                                    fontSize = 12.5.sp,
                                    lineHeight = 17.sp
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = phoneInput,
                        onValueChange = { phoneInput = it },
                        label = { Text("ফোন নম্বর (Phone Number)", color = Color(0xFF94A3B8)) },
                        placeholder = { Text("যেমন: 01960454682", color = Color(0xFF64748B)) },
                        leadingIcon = {
                            Icon(Icons.Rounded.PhoneAndroid, contentDescription = null, tint = Color(0xFF00E5FF))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        colors = customFieldColors(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("subscription_phone_input")
                    )

                    OutlinedTextField(
                        value = voucherInput,
                        onValueChange = { voucherInput = it },
                        label = { Text("ভাউচার কোড (Voucher Code)", color = Color(0xFF94A3B8)) },
                        placeholder = { Text("যেমন: 8936", color = Color(0xFF64748B)) },
                        leadingIcon = {
                            Icon(Icons.Rounded.Lock, contentDescription = null, tint = Color(0xFF10B981))
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        colors = customFieldColors(),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("subscription_voucher_input")
                    )

                    Button(
                        onClick = {
                            if (isSubmitting) return@Button
                            isSubmitting = true
                            inlineError = null
                            scope.launch {
                                val (ok, result) = repository.verifyAndActivateSubscription(phoneInput, voucherInput)
                                isSubmitting = false
                                when {
                                    ok && result is SubscriptionCheckResult.Active -> {
                                        Toast.makeText(
                                            context,
                                            "সাবস্ক্রিপশন সক্রিয় (${result.record.packageType}) • মেয়াদ: ${result.record.expiryDate}",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        onActivated(result)
                                    }
                                    result is SubscriptionCheckResult.ExpiredOrInactive -> {
                                        inlineError = result.reason
                                    }
                                    result is SubscriptionCheckResult.NetworkError -> {
                                        inlineError = "নেটওয়ার্ক ত্রুটি: ${result.message}"
                                    }
                                    else -> {
                                        inlineError = "যাচাই করা যায়নি। পুনরায় চেষ্টা করুন।"
                                    }
                                }
                            }
                        },
                        enabled = !isSubmitting,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF00E5FF),
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .testTag("subscription_verify_button")
                    ) {
                        if (isSubmitting) {
                            CircularProgressIndicator(
                                color = Color.Black,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                        } else {
                            Icon(Icons.Rounded.CheckCircle, contentDescription = null, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        Text(
                            text = if (isSubmitting) "যাচাই করা হচ্ছে..." else "ভেরিফাই ও প্রবেশ করুন",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    OutlinedButton(
                        onClick = onRetryStartupCheck,
                        enabled = !isChecking && !isSubmitting,
                        border = BorderStroke(1.dp, Color(0xFF334155)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFCBD5E1)),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .testTag("subscription_recheck_button")
                    ) {
                        Icon(Icons.Rounded.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("মেয়াদ ও স্ট্যাটাস পুনরায় চেক করুন", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    if (supportContact.isNotBlank()) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color(0xFF1E293B),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "ভাউচার পেতে যোগাযোগ: $supportContact",
                                color = Color(0xFF38BDF8),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(10.dp)
                            )
                        }
                    }
                }
            }

            // Discreet Admin Access so owner can always configure Firebase / users
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .focusable()
                    .clickable { onOpenAdminLogin() }
                    .defaultMinSize(minHeight = 48.dp)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .testTag("subscription_admin_trigger"),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Security,
                    contentDescription = "Admin Access",
                    tint = Color(0xFF64748B),
                    modifier = Modifier.size(15.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Admin Access",
                    color = Color(0xFF64748B),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

/**
 * Menu tab card displaying current subscriber info, expiry countdown, and voucher renewal button.
 */
@Composable
fun MySubscriptionMenuCard(
    result: SubscriptionCheckResult,
    savedPhone: String,
    onRenewOrVerifyClick: () -> Unit,
    onRefreshStatusClick: () -> Unit,
    onSwitchPhoneClick: () -> Unit
) {
    val activeRecord = (result as? SubscriptionCheckResult.Active)?.record
        ?: (result as? SubscriptionCheckResult.NetworkError)?.cachedActive?.record
    val remaining = (result as? SubscriptionCheckResult.Active)?.remainingDays
        ?: (result as? SubscriptionCheckResult.NetworkError)?.cachedActive?.remainingDays

    val isGateDisabled = result is SubscriptionCheckResult.Disabled
    val isActive = activeRecord != null

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
        border = BorderStroke(
            1.dp,
            when {
                isActive -> Color(0xFF10B981).copy(alpha = 0.65f)
                isGateDisabled -> Color(0xFF334155)
                else -> Color(0xFFF59E0B).copy(alpha = 0.65f)
            }
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = if (isActive) Color(0xFF10B981).copy(alpha = 0.16f) else Color(0xFF38BDF8).copy(alpha = 0.16f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = if (isActive) Icons.Rounded.CheckCircle else Icons.Rounded.PhoneAndroid,
                                contentDescription = "Subscription Status",
                                tint = if (isActive) Color(0xFF10B981) else Color(0xFF38BDF8),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "আমার সাবস্ক্রিপশন (Subscription)",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = when {
                                isActive -> "সক্রিয় (${activeRecord!!.packageType.ifBlank { "Active" }})"
                                isGateDisabled -> "ফ্রি এক্সেস চালু রয়েছে"
                                savedPhone.isNotBlank() -> "নম্বর: $savedPhone"
                                else -> "ভাউচার যুক্ত করা হয়নি"
                            },
                            color = if (isActive) Color(0xFF6EE7B7) else Color(0xFF94A3B8),
                            fontSize = 12.sp
                        )
                    }
                }

                if (remaining != null && remaining >= 0) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (remaining <= 5) Color(0xFFF59E0B).copy(alpha = 0.2f) else Color(0xFF10B981).copy(alpha = 0.2f)
                    ) {
                        Text(
                            text = "$remaining দিন বাকি",
                            color = if (remaining <= 5) Color(0xFFFBBF24) else Color(0xFF6EE7B7),
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            if (activeRecord != null) {
                Surface(
                    shape = RoundedCornerShape(10.dp),
                    color = Color(0xFF0F172A),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text("নম্বর: ${activeRecord.phone}", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                            if (activeRecord.voucherCode.isNotBlank()) {
                                Text("ভাউচার: ${activeRecord.voucherCode}", color = Color(0xFF94A3B8), fontSize = 11.sp)
                            }
                        }
                        Column(horizontalAlignment = Alignment.End) {
                            Text("মেয়াদ: ${activeRecord.expiryDate}", color = Color(0xFF00E5FF), fontSize = 12.sp, fontWeight = FontWeight.Bold)
                            Text("প্যাকেজ: ${activeRecord.packageType}", color = Color(0xFF94A3B8), fontSize = 11.sp)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRenewOrVerifyClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF00E5FF),
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .weight(1f)
                        .defaultMinSize(minHeight = 48.dp)
                ) {
                    Text(
                        text = if (isActive) "রিনিউ / ভাউচার" else "অ্যাক্টিভ করুন",
                        fontSize = 12.5.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                OutlinedButton(
                    onClick = onRefreshStatusClick,
                    border = BorderStroke(1.dp, Color(0xFF334155)),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                ) {
                    Icon(Icons.Rounded.Refresh, contentDescription = "Refresh Subscription", modifier = Modifier.size(16.dp))
                }

                if (savedPhone.isNotBlank()) {
                    OutlinedButton(
                        onClick = onSwitchPhoneClick,
                        border = BorderStroke(1.dp, Color(0xFFEF4444).copy(alpha = 0.5f)),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFFFCA5A5)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                    ) {
                        Icon(Icons.Rounded.Close, contentDescription = "Logout Subscriber", modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

/**
 * Admin Panel Tab (`AdminTab.SUBSCRIPTIONS`) to manage `/users/{phone}/` records in Firebase RTDB
 * and toggle the app-launch Subscription Gate.
 */
@Composable
fun AdminSubscriptionManagementContent(
    repository: MediaRepository
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var gateEnabled by remember { mutableStateOf(repository.isSubscriptionGateEnabledLocal()) }
    var supportContact by remember { mutableStateOf(repository.getSubscriptionSupportContactLocal()) }
    var usersList by remember { mutableStateOf<List<UserSubscriptionRecord>>(emptyList()) }
    var isLoadingUsers by remember { mutableStateOf(true) }
    var searchQuery by remember { mutableStateOf("") }

    // Form fields
    var phoneInput by remember { mutableStateOf("") }
    var voucherInput by remember { mutableStateOf(SubscriptionManager.generateVoucherCode()) }
    var packageTypeInput by remember { mutableStateOf("1Month") }
    var expiryDateInput by remember { mutableStateOf(SubscriptionManager.addMonthsToDateString(1)) }
    var statusInput by remember { mutableStateOf("active") }
    var isSaving by remember { mutableStateOf(false) }

    fun reloadFromRtdb() {
        isLoadingUsers = true
        scope.launch {
            val (remoteGate, remoteSupport) = repository.fetchSubscriptionSettingsFromRtdb()
            val list = repository.fetchAllSubscribedUsersFromRtdb()
            gateEnabled = remoteGate
            supportContact = remoteSupport
            usersList = list
            isLoadingUsers = false
        }
    }

    LaunchedEffect(Unit) {
        reloadFromRtdb()
    }

    val filteredUsers = remember(usersList, searchQuery) {
        if (searchQuery.isBlank()) usersList
        else usersList.filter {
            it.phone.contains(searchQuery, ignoreCase = true) ||
                it.voucherCode.contains(searchQuery, ignoreCase = true) ||
                it.packageType.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // 1. Launch Gate Toggle Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.dp, if (gateEnabled) Color(0xFF10B981) else Color(0xFF475569)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f).padding(end = 8.dp)) {
                        Text(
                            text = "অ্যাপ ওপেনে সাবস্ক্রিপশন গেট (Launch Lock)",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = if (gateEnabled)
                                "চালু আছে: /users/{phone}/ যাচাই ছাড়া ড্যাশবোর্ডে প্রবেশ বন্ধ থাকবে"
                            else
                                "বন্ধ আছে: সকল ইউজার সরাসরি ড্যাশবোর্ডে প্রবেশ করতে পারবে",
                            color = if (gateEnabled) Color(0xFF6EE7B7) else Color(0xFF94A3B8),
                            fontSize = 12.sp
                        )
                    }
                    Switch(
                        checked = gateEnabled,
                        onCheckedChange = { checked ->
                            gateEnabled = checked
                            scope.launch {
                                val (ok, msg) = repository.saveSubscriptionSettingsToRtdb(checked, supportContact)
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                if (!ok) gateEnabled = !checked
                            }
                        }
                    )
                }

                OutlinedTextField(
                    value = supportContact,
                    onValueChange = { supportContact = it },
                    label = { Text("ভাউচার কেনার যোগাযোগ (WhatsApp / Telegram)", color = Color(0xFF94A3B8)) },
                    placeholder = { Text("যেমন: WhatsApp: 019XXXXXXXX", color = Color(0xFF64748B)) },
                    singleLine = true,
                    colors = customFieldColors(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        scope.launch {
                            val (_, msg) = repository.saveSubscriptionSettingsToRtdb(gateEnabled, supportContact)
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0284C7)),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp)
                ) {
                    Text("সাবস্ক্রিপশন সেটিংস সেভ করুন", fontWeight = FontWeight.Bold)
                }
            }
        }

        // 2. Add / Edit User Subscription Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.dp, Color(0xFF00E5FF).copy(alpha = 0.5f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "নতুন ইউজার / ভাউচার যোগ ও আপডেট (/users/{phone}/)",
                    color = Color.White,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )

                OutlinedTextField(
                    value = phoneInput,
                    onValueChange = { phoneInput = it },
                    label = { Text("ইউজার ফোন নম্বর", color = Color(0xFF94A3B8)) },
                    placeholder = { Text("01960454682", color = Color(0xFF64748B)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    singleLine = true,
                    colors = customFieldColors(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = voucherInput,
                        onValueChange = { voucherInput = it },
                        label = { Text("ভাউচার কোড", color = Color(0xFF94A3B8)) },
                        placeholder = { Text("8936", color = Color(0xFF64748B)) },
                        singleLine = true,
                        colors = customFieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    )
                    Button(
                        onClick = { voucherInput = SubscriptionManager.generateVoucherCode() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF334155)),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.defaultMinSize(minHeight = 48.dp)
                    ) {
                        Text("নতুন কোড", fontSize = 12.sp)
                    }
                }

                // Quick Package Presets
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    listOf("1Month" to 1, "3Months" to 3, "6Months" to 6, "1Year" to 12).forEach { (label, months) ->
                        val selected = packageTypeInput == label
                        OutlinedButton(
                            onClick = {
                                packageTypeInput = label
                                expiryDateInput = SubscriptionManager.addMonthsToDateString(months)
                            },
                            border = BorderStroke(1.dp, if (selected) Color(0xFF00E5FF) else Color(0xFF334155)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                containerColor = if (selected) Color(0xFF00E5FF).copy(alpha = 0.16f) else Color.Transparent,
                                contentColor = if (selected) Color(0xFF00E5FF) else Color.White
                            ),
                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                            modifier = Modifier.weight(1f).defaultMinSize(minHeight = 48.dp)
                        ) {
                            Text(label, fontSize = 11.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = expiryDateInput,
                        onValueChange = { expiryDateInput = it },
                        label = { Text("মেয়াদ (YYYY-MM-DD)", color = Color(0xFF94A3B8)) },
                        placeholder = { Text("2026-12-17", color = Color(0xFF64748B)) },
                        singleLine = true,
                        colors = customFieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = statusInput,
                        onValueChange = { statusInput = it },
                        label = { Text("Status", color = Color(0xFF94A3B8)) },
                        placeholder = { Text("active", color = Color(0xFF64748B)) },
                        singleLine = true,
                        colors = customFieldColors(),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(0.75f)
                    )
                }

                Button(
                    onClick = {
                        if (isSaving) return@Button
                        if (!SubscriptionManager.isDateValid(expiryDateInput) && statusInput.equals("active", true)) {
                            Toast.makeText(context, "সঠিক ভবিষ্যৎ তারিখ (YYYY-MM-DD) দিন", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        isSaving = true
                        scope.launch {
                            val record = UserSubscriptionRecord(
                                phone = phoneInput,
                                subscriptionStatus = statusInput.trim().ifBlank { "active" },
                                expiryDate = expiryDateInput.trim(),
                                packageType = packageTypeInput.trim().ifBlank { "1Month" },
                                voucherCode = voucherInput.trim()
                            )
                            val (ok, msg) = repository.saveUserSubscriptionToRtdb(record)
                            isSaving = false
                            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
                            if (ok) reloadFromRtdb()
                        }
                    },
                    enabled = !isSaving,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF10B981), contentColor = Color.Black),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp)
                ) {
                    Icon(Icons.Rounded.AddCircleOutline, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(if (isSaving) "Firebase-এ সেভ হচ্ছে..." else "Firebase-এ সেভ / আপডেট করুন", fontWeight = FontWeight.Bold)
                }
            }
        }

        // 3. Subscriber List Card
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF1E293B)),
            border = BorderStroke(1.dp, Color(0xFF334155)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "সাবস্ক্রাইবার তালিকা (${filteredUsers.size})",
                        color = Color.White,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    IconButton(onClick = { reloadFromRtdb() }) {
                        Icon(Icons.Rounded.Refresh, contentDescription = "Refresh Users", tint = Color(0xFF00E5FF))
                    }
                }

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text("ফোন নম্বর বা ভাউচার দিয়ে খুঁজুন...", color = Color(0xFF64748B)) },
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null, tint = Color(0xFF64748B)) },
                    singleLine = true,
                    colors = customFieldColors(),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                )

                if (isLoadingUsers) {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = Color(0xFF00E5FF))
                    }
                } else if (filteredUsers.isEmpty()) {
                    Text(
                        text = "এখনো কোনো ইউজার পাওয়া যায়নি। উপরের ফর্ম থেকে ফোন নম্বর ও ভাউচার কোড যুক্ত করুন।",
                        color = Color(0xFF94A3B8),
                        fontSize = 12.5.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    filteredUsers.forEach { user ->
                        val eval = SubscriptionManager.evaluateRecord(user)
                        val isCurrentlyValid = eval is SubscriptionCheckResult.Active
                        val remDays = (eval as? SubscriptionCheckResult.Active)?.remainingDays

                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFF0F172A),
                            border = BorderStroke(
                                1.dp,
                                if (isCurrentlyValid) Color(0xFF10B981).copy(alpha = 0.45f) else Color(0xFFEF4444).copy(alpha = 0.45f)
                            ),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column {
                                        Text(user.phone, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                        Text(
                                            text = "প্যাকেজ: ${user.packageType} • ভাউচার: ${user.voucherCode.ifBlank { "N/A" }}",
                                            color = Color(0xFF94A3B8),
                                            fontSize = 11.5.sp
                                        )
                                        Text(
                                            text = "মেয়াদ: ${user.expiryDate}" + if (remDays != null) " ($remDays দিন)" else "",
                                            color = if (isCurrentlyValid) Color(0xFF00E5FF) else Color(0xFFFCA5A5),
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.Medium
                                        )
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = if (isCurrentlyValid) Color(0xFF10B981).copy(alpha = 0.2f) else Color(0xFFEF4444).copy(alpha = 0.2f)
                                    ) {
                                        Text(
                                            text = if (isCurrentlyValid) "ACTIVE" else user.subscriptionStatus.uppercase().ifBlank { "EXPIRED" },
                                            color = if (isCurrentlyValid) Color(0xFF6EE7B7) else Color(0xFFFCA5A5),
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.ExtraBold,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    OutlinedButton(
                                        onClick = {
                                            val extended = user.copy(
                                                subscriptionStatus = "active",
                                                expiryDate = SubscriptionManager.addMonthsToDateString(1, user.expiryDate)
                                            )
                                            scope.launch {
                                                val (_, msg) = repository.saveUserSubscriptionToRtdb(extended)
                                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                                reloadFromRtdb()
                                            }
                                        },
                                        border = BorderStroke(1.dp, Color(0xFF10B981)),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF6EE7B7)),
                                        modifier = Modifier.weight(1f).defaultMinSize(minHeight = 48.dp)
                                    ) {
                                        Icon(Icons.Rounded.Check, contentDescription = null, modifier = Modifier.size(14.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("+১ মাস রিনিউ", fontSize = 11.5.sp)
                                    }

                                    OutlinedButton(
                                        onClick = {
                                            val nextStatus = if (user.subscriptionStatus.equals("active", true)) "inactive" else "active"
                                            scope.launch {
                                                val (_, msg) = repository.saveUserSubscriptionToRtdb(user.copy(subscriptionStatus = nextStatus))
                                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                                reloadFromRtdb()
                                            }
                                        },
                                        border = BorderStroke(1.dp, Color(0xFF38BDF8)),
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color(0xFF38BDF8)),
                                        modifier = Modifier.weight(1f).defaultMinSize(minHeight = 48.dp)
                                    ) {
                                        Text(
                                            if (user.subscriptionStatus.equals("active", true)) "বন্ধ করুন" else "চালু করুন",
                                            fontSize = 11.5.sp
                                        )
                                    }

                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                val (_, msg) = repository.deleteUserSubscriptionFromRtdb(user.phone)
                                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                                                reloadFromRtdb()
                                            }
                                        },
                                        modifier = Modifier.defaultMinSize(minWidth = 48.dp, minHeight = 48.dp)
                                    ) {
                                        Icon(Icons.Rounded.DeleteSweep, contentDescription = "Delete User", tint = Color(0xFFEF4444))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
