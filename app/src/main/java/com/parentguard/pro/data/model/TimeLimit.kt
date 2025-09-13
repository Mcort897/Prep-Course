package com.parentguard.pro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "time_limits")
@Parcelize
data class TimeLimit(
    @PrimaryKey
    val id: String,
    val childId: String,
    val name: String,
    val type: TimeLimitType,
    val duration: Long, // in milliseconds
    val startTime: String? = null, // HH:mm format for daily limits
    val endTime: String? = null, // HH:mm format for daily limits
    val daysOfWeek: List<Int> = emptyList(), // 1=Monday, 7=Sunday
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val lastModifiedAt: Date = Date(),
    val autoExtend: Boolean = false,
    val maxExtensions: Int = 0,
    val extensionDuration: Long = 0, // in milliseconds
    val warningThreshold: Long = 0, // in milliseconds before limit
    val blockOnExceed: Boolean = true,
    val allowOverride: Boolean = false,
    val overridePassword: String? = null
) : Parcelable

@Parcelize
enum class TimeLimitType : Parcelable {
    DAILY_SCREEN_TIME, // Total daily screen time
    APP_SPECIFIC, // Time limit for specific app
    CATEGORY_SPECIFIC, // Time limit for app category
    BEDTIME, // Bedtime restrictions
    STUDY_TIME, // Study time requirements
    BREAK_TIME, // Mandatory break time
    CUSTOM // Custom time limit
}

@Entity(tableName = "time_limit_usage")
@Parcelize
data class TimeLimitUsage(
    @PrimaryKey
    val id: String,
    val timeLimitId: String,
    val childId: String,
    val date: Date,
    val usedTime: Long, // in milliseconds
    val remainingTime: Long, // in milliseconds
    val isExceeded: Boolean = false,
    val exceededBy: Long = 0, // in milliseconds
    val extensionsUsed: Int = 0,
    val overridesUsed: Int = 0,
    val warningsSent: Int = 0,
    val lastWarningAt: Date? = null
) : Parcelable

@Entity(tableName = "time_limit_violations")
@Parcelize
data class TimeLimitViolation(
    @PrimaryKey
    val id: String,
    val timeLimitId: String,
    val childId: String,
    val violationType: ViolationType,
    val violatedAt: Date = Date(),
    val exceededBy: Long, // in milliseconds
    val appPackageName: String? = null,
    val appName: String? = null,
    val category: AppCategory? = null,
    val isResolved: Boolean = false,
    val resolvedAt: Date? = null,
    val resolutionType: ResolutionType? = null,
    val notes: String? = null
) : Parcelable

@Parcelize
enum class ViolationType : Parcelable {
    TIME_LIMIT_EXCEEDED,
    BEDTIME_VIOLATION,
    BLOCKED_APP_ACCESS,
    RESTRICTED_AREA_ACCESS,
    STUDY_TIME_NOT_MET,
    BREAK_TIME_NOT_TAKEN
}

@Parcelize
enum class ResolutionType : Parcelable {
    AUTOMATIC_BLOCK,
    PARENT_OVERRIDE,
    CHILD_APPEAL,
    SYSTEM_ADJUSTMENT,
    TIME_EXTENSION_GRANTED
}