package com.parentalcontrolpro.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class AppUsage(
    val id: String = "",
    val userId: String = "",
    val packageName: String = "",
    val appName: String = "",
    val usageTime: Long = 0, // in milliseconds
    val launchCount: Int = 0,
    val lastUsed: Date = Date(),
    val date: Date = Date(),
    val isBlocked: Boolean = false,
    val isAllowed: Boolean = true,
    val category: AppCategory = AppCategory.OTHER,
    val icon: String = ""
) : Parcelable

@Parcelize
data class AppCategory(
    val id: String = "",
    val name: String = "",
    val color: String = "",
    val icon: String = ""
) : Parcelable

enum class AppCategoryType {
    EDUCATION,
    GAMES,
    SOCIAL,
    ENTERTAINMENT,
    PRODUCTIVITY,
    COMMUNICATION,
    NEWS,
    SHOPPING,
    FINANCE,
    HEALTH,
    TRAVEL,
    PHOTOGRAPHY,
    MUSIC,
    VIDEO,
    BOOKS,
    SPORTS,
    WEATHER,
    TOOLS,
    OTHER
}

@Parcelize
data class WebActivity(
    val id: String = "",
    val userId: String = "",
    val url: String = "",
    val title: String = "",
    val domain: String = "",
    val visitTime: Date = Date(),
    val duration: Long = 0, // in milliseconds
    val isBlocked: Boolean = false,
    val isAllowed: Boolean = true,
    val category: WebCategory = WebCategory.OTHER,
    val thumbnail: String = ""
) : Parcelable

@Parcelize
data class WebCategory(
    val id: String = "",
    val name: String = "",
    val color: String = "",
    val icon: String = ""
) : Parcelable

enum class WebCategoryType {
    EDUCATION,
    NEWS,
    SOCIAL,
    ENTERTAINMENT,
    SHOPPING,
    GAMES,
    ADULT,
    VIOLENCE,
    GAMBLING,
    DRUGS,
    WEAPONS,
    HATE_SPEECH,
    OTHER
}

@Parcelize
data class LocationData(
    val id: String = "",
    val userId: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val accuracy: Float = 0f,
    val altitude: Double = 0.0,
    val speed: Float = 0f,
    val bearing: Float = 0f,
    val timestamp: Date = Date(),
    val address: String = "",
    val isInSafeZone: Boolean = false,
    val isInDangerZone: Boolean = false,
    val safeZoneId: String? = null,
    val dangerZoneId: String? = null
) : Parcelable

@Parcelize
data class CallLog(
    val id: String = "",
    val userId: String = "",
    val phoneNumber: String = "",
    val contactName: String = "",
    val callType: CallType = CallType.OUTGOING,
    val duration: Long = 0, // in seconds
    val timestamp: Date = Date(),
    val isBlocked: Boolean = false
) : Parcelable

enum class CallType {
    INCOMING,
    OUTGOING,
    MISSED,
    REJECTED,
    BLOCKED
}

@Parcelize
data class Message(
    val id: String = "",
    val userId: String = "",
    val phoneNumber: String = "",
    val contactName: String = "",
    val content: String = "",
    val messageType: MessageType = MessageType.SMS,
    val timestamp: Date = Date(),
    val isIncoming: Boolean = true,
    val isBlocked: Boolean = false
) : Parcelable

enum class MessageType {
    SMS,
    MMS,
    WHATSAPP,
    TELEGRAM,
    FACEBOOK,
    INSTAGRAM,
    SNAPCHAT,
    OTHER
}

@Parcelize
data class TimeLimit(
    val id: String = "",
    val userId: String = "",
    val appPackageName: String? = null, // null for general time limit
    val dailyLimit: Int = 0, // in minutes
    val usedTime: Int = 0, // in minutes
    val remainingTime: Int = 0, // in minutes
    val isActive: Boolean = true,
    val startTime: String = "00:00",
    val endTime: String = "23:59",
    val daysOfWeek: List<Int> = listOf(1, 2, 3, 4, 5, 6, 7), // 1 = Monday, 7 = Sunday
    val isExceeded: Boolean = false,
    val lastReset: Date = Date()
) : Parcelable

@Parcelize
data class Alert(
    val id: String = "",
    val userId: String = "",
    val parentId: String = "",
    val type: AlertType = AlertType.TIME_LIMIT,
    val title: String = "",
    val message: String = "",
    val timestamp: Date = Date(),
    val isRead: Boolean = false,
    val isResolved: Boolean = false,
    val priority: AlertPriority = AlertPriority.MEDIUM,
    val data: Map<String, Any> = emptyMap()
) : Parcelable

enum class AlertType {
    TIME_LIMIT,
    LOCATION,
    CONTENT_BLOCKED,
    APP_BLOCKED,
    EMERGENCY,
    SAFE_ZONE_EXIT,
    DANGER_ZONE_ENTRY,
    SUSPICIOUS_ACTIVITY,
    BATTERY_LOW,
    DEVICE_OFFLINE
}

enum class AlertPriority {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

@Parcelize
data class Report(
    val id: String = "",
    val userId: String = "",
    val parentId: String = "",
    val reportType: ReportType = ReportType.DAILY,
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val totalScreenTime: Long = 0, // in minutes
    val mostUsedApps: List<AppUsage> = emptyList(),
    val mostVisitedSites: List<WebActivity> = emptyList(),
    val locationData: List<LocationData> = emptyList(),
    val callLogs: List<CallLog> = emptyList(),
    val messages: List<Message> = emptyList(),
    val alerts: List<Alert> = emptyList(),
    val isGenerated: Boolean = false,
    val createdAt: Date = Date()
) : Parcelable

enum class ReportType {
    DAILY,
    WEEKLY,
    MONTHLY,
    CUSTOM
}