package com.safeguard.parental.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class AppUsage(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val packageName: String = "",
    val appName: String = "",
    val category: String = "",
    val usageTimeMillis: Long = 0L,
    val launchCount: Int = 0,
    val lastUsed: Date? = null,
    val date: String = "", // YYYY-MM-DD format
    @ServerTimestamp
    val createdAt: Date? = null,
    val icon: String = "" // Base64 encoded icon or URL
)

data class AppInfo(
    val packageName: String = "",
    val appName: String = "",
    val category: String = "",
    val version: String = "",
    val isSystemApp: Boolean = false,
    val isInstalled: Boolean = true,
    val installDate: Date? = null,
    val lastUpdateDate: Date? = null,
    val icon: String = "",
    val permissions: List<String> = emptyList(),
    val dataUsageMB: Long = 0L
)

data class AppSession(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val packageName: String = "",
    val appName: String = "",
    val startTime: Date? = null,
    val endTime: Date? = null,
    val durationMillis: Long = 0L,
    val isActive: Boolean = false,
    val screenshots: List<String> = emptyList(), // URLs to screenshots
    val keystrokes: Int = 0,
    val touches: Int = 0
)

data class DailyAppReport(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val date: String = "", // YYYY-MM-DD format
    val totalScreenTimeMillis: Long = 0L,
    val appUsages: List<AppUsage> = emptyList(),
    val mostUsedApp: String = "",
    val appsBlocked: Int = 0,
    val limitsExceeded: Int = 0,
    val pickups: Int = 0, // Device pickups
    val notifications: Int = 0,
    @ServerTimestamp
    val generatedAt: Date? = null
)

data class AppBlock(
    @DocumentId
    val id: String = "",
    val childId: String = "",
    val packageName: String = "",
    val appName: String = "",
    val reason: BlockReason = BlockReason.TIME_LIMIT_EXCEEDED,
    val blockStart: Date? = null,
    val blockEnd: Date? = null,
    val isActive: Boolean = true,
    val canRequestUnblock: Boolean = true,
    val unblockRequests: Int = 0,
    @ServerTimestamp
    val createdAt: Date? = null
)

enum class BlockReason {
    TIME_LIMIT_EXCEEDED,
    SCHEDULE_RESTRICTION,
    MANUAL_BLOCK,
    INAPPROPRIATE_CONTENT,
    BEDTIME_MODE,
    STUDY_MODE
}

data class AppCategory(
    val name: String = "",
    val displayName: String = "",
    val color: String = "#2196F3",
    val icon: String = "",
    val defaultLimitMinutes: Int = 60,
    val ageRestrictions: Map<String, Int> = emptyMap(), // age range to limit
    val isEducational: Boolean = false
)