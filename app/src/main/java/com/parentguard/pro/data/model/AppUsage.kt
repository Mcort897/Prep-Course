package com.parentguard.pro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "app_usage")
@Parcelize
data class AppUsage(
    @PrimaryKey
    val id: String,
    val childId: String,
    val packageName: String,
    val appName: String,
    val usageTime: Long, // in milliseconds
    val sessionCount: Int = 1,
    val date: Date = Date(),
    val isBlocked: Boolean = false,
    val category: AppCategory = AppCategory.OTHER,
    val lastUsedAt: Date = Date()
) : Parcelable

@Parcelize
enum class AppCategory : Parcelable {
    SOCIAL_MEDIA,
    GAMES,
    EDUCATION,
    PRODUCTIVITY,
    ENTERTAINMENT,
    COMMUNICATION,
    NEWS,
    SHOPPING,
    FINANCE,
    HEALTH,
    OTHER
}

@Entity(tableName = "app_usage_daily")
@Parcelize
data class AppUsageDaily(
    @PrimaryKey
    val id: String,
    val childId: String,
    val date: Date,
    val totalScreenTime: Long, // in milliseconds
    val appUsages: List<AppUsage> = emptyList(),
    val mostUsedApp: String? = null,
    val mostUsedCategory: AppCategory? = null,
    val averageSessionTime: Long = 0, // in milliseconds
    val totalSessions: Int = 0
) : Parcelable

@Entity(tableName = "app_usage_weekly")
@Parcelize
data class AppUsageWeekly(
    @PrimaryKey
    val id: String,
    val childId: String,
    val weekStartDate: Date,
    val weekEndDate: Date,
    val totalScreenTime: Long, // in milliseconds
    val dailyUsages: List<AppUsageDaily> = emptyList(),
    val averageDailyTime: Long = 0, // in milliseconds
    val mostUsedApps: List<String> = emptyList(),
    val categoryBreakdown: Map<AppCategory, Long> = emptyMap()
) : Parcelable

@Entity(tableName = "app_usage_monthly")
@Parcelize
data class AppUsageMonthly(
    @PrimaryKey
    val id: String,
    val childId: String,
    val month: Int,
    val year: Int,
    val totalScreenTime: Long, // in milliseconds
    val weeklyUsages: List<AppUsageWeekly> = emptyList(),
    val averageDailyTime: Long = 0, // in milliseconds
    val totalAppsUsed: Int = 0,
    val topCategories: List<AppCategory> = emptyList()
) : Parcelable