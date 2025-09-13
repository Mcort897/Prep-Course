package com.parentguard.pro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "rewards")
@Parcelize
data class Reward(
    @PrimaryKey
    val id: String,
    val childId: String,
    val name: String,
    val description: String,
    val pointsRequired: Int,
    val category: RewardCategory,
    val iconUrl: String? = null,
    val isActive: Boolean = true,
    val isRedeemed: Boolean = false,
    val redeemedAt: Date? = null,
    val createdAt: Date = Date(),
    val expiresAt: Date? = null,
    val parentApprovalRequired: Boolean = false,
    val isApproved: Boolean = false,
    val approvedAt: Date? = null,
    val approvedBy: String? = null
) : Parcelable

@Parcelize
enum class RewardCategory : Parcelable {
    SCREEN_TIME_EXTENSION,
    GAME_TIME,
    SPECIAL_PRIVILEGES,
    PHYSICAL_REWARDS,
    EXPERIENCES,
    CUSTOM
}

@Entity(tableName = "achievements")
@Parcelize
data class Achievement(
    @PrimaryKey
    val id: String,
    val childId: String,
    val name: String,
    val description: String,
    val category: AchievementCategory,
    val pointsAwarded: Int,
    val iconUrl: String? = null,
    val isUnlocked: Boolean = false,
    val unlockedAt: Date? = null,
    val progress: Int = 0,
    val maxProgress: Int = 100,
    val createdAt: Date = Date(),
    val requirements: AchievementRequirements? = null
) : Parcelable

@Parcelize
enum class AchievementCategory : Parcelable {
    STUDY_TIME,
    EXERCISE,
    CHORES,
    BEHAVIOR,
    LEARNING,
    CREATIVITY,
    SOCIAL,
    HEALTHY_HABITS
}

@Parcelize
data class AchievementRequirements(
    val type: AchievementType,
    val targetValue: Int,
    val timeFrame: TimeFrame? = null,
    val specificApps: List<String> = emptyList(),
    val specificCategories: List<AppCategory> = emptyList(),
    val description: String? = null
) : Parcelable

@Parcelize
enum class AchievementType : Parcelable {
    SCREEN_TIME_LIMIT,
    STUDY_TIME_TARGET,
    EXERCISE_TIME,
    CHORE_COMPLETION,
    BEHAVIOR_SCORE,
    LEARNING_ACTIVITIES,
    CREATIVE_ACTIVITIES,
    SOCIAL_INTERACTIONS,
    HEALTHY_HABITS
}

@Parcelize
enum class TimeFrame : Parcelable {
    DAILY,
    WEEKLY,
    MONTHLY,
    YEARLY,
    ALL_TIME
}

@Entity(tableName = "points")
@Parcelize
data class Points(
    @PrimaryKey
    val id: String,
    val childId: String,
    val points: Int,
    val source: PointsSource,
    val description: String,
    val earnedAt: Date = Date(),
    val isSpent: Boolean = false,
    val spentAt: Date? = null,
    val spentOn: String? = null
) : Parcelable

@Parcelize
enum class PointsSource : Parcelable {
    ACHIEVEMENT_UNLOCKED,
    DAILY_GOAL_MET,
    WEEKLY_GOAL_MET,
    MONTHLY_GOAL_MET,
    BEHAVIOR_REWARD,
    STUDY_TIME_BONUS,
    EXERCISE_BONUS,
    CHORE_COMPLETION,
    PARENT_BONUS,
    MANUAL_AWARD
}