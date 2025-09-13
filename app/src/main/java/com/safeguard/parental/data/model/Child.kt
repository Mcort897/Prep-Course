package com.safeguard.parental.data.model

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

data class Child(
    @DocumentId
    val id: String = "",
    val parentId: String = "",
    val name: String = "",
    val age: Int = 0,
    val avatarUrl: String = "",
    val deviceId: String = "",
    val deviceName: String = "",
    val isActive: Boolean = true,
    val status: ChildStatus = ChildStatus.OFFLINE,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null,
    val lastSeen: Date? = null,
    val currentLocation: Location? = null,
    val appLimits: Map<String, AppLimit> = emptyMap(),
    val safeZones: List<String> = emptyList(), // SafeZone IDs
    val blockedApps: List<String> = emptyList(),
    val allowedApps: List<String> = emptyList(),
    val webFilters: WebFilters = WebFilters(),
    val schedules: List<Schedule> = emptyList()
)

enum class ChildStatus {
    ONLINE,
    OFFLINE,
    ACTIVE,
    IDLE,
    RESTRICTED
}

data class AppLimit(
    val packageName: String = "",
    val dailyLimitMinutes: Int = 0,
    val usedTodayMinutes: Int = 0,
    val isBlocked: Boolean = false,
    val timeSlots: List<TimeSlot> = emptyList(),
    val lastUsed: Date? = null
)

data class TimeSlot(
    val startHour: Int = 0,
    val startMinute: Int = 0,
    val endHour: Int = 23,
    val endMinute: Int = 59,
    val daysOfWeek: List<Int> = (1..7).toList() // 1=Monday, 7=Sunday
)

data class WebFilters(
    val enabled: Boolean = true,
    val safeSearchEnabled: Boolean = true,
    val blockedCategories: List<String> = emptyList(),
    val blockedDomains: List<String> = emptyList(),
    val allowedDomains: List<String> = emptyList(),
    val blockAdultContent: Boolean = true,
    val blockViolence: Boolean = true,
    val blockGambling: Boolean = true
)

data class Schedule(
    val id: String = "",
    val name: String = "",
    val startTime: String = "00:00", // HH:mm format
    val endTime: String = "23:59",
    val daysOfWeek: List<Int> = emptyList(),
    val restrictions: ScheduleRestrictions = ScheduleRestrictions(),
    val isActive: Boolean = true
)

data class ScheduleRestrictions(
    val blockAllApps: Boolean = false,
    val allowedApps: List<String> = emptyList(),
    val blockedApps: List<String> = emptyList(),
    val screenTimeLimitMinutes: Int = 0,
    val requireLocationTracking: Boolean = false
)