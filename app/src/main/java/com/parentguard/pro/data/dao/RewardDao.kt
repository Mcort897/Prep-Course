package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface RewardDao {
    
    // Rewards
    @Query("SELECT * FROM rewards WHERE childId = :childId ORDER BY createdAt DESC")
    suspend fun getRewardsByChildId(childId: String): List<Reward>
    
    @Query("SELECT * FROM rewards WHERE childId = :childId ORDER BY createdAt DESC")
    fun getRewardsByChildIdFlow(childId: String): Flow<List<Reward>>
    
    @Query("SELECT * FROM rewards WHERE childId = :childId AND isActive = 1 ORDER BY pointsRequired ASC")
    suspend fun getActiveRewardsByChildId(childId: String): List<Reward>
    
    @Query("SELECT * FROM rewards WHERE childId = :childId AND isActive = 1 ORDER BY pointsRequired ASC")
    fun getActiveRewardsByChildIdFlow(childId: String): Flow<List<Reward>>
    
    @Query("SELECT * FROM rewards WHERE childId = :childId AND isRedeemed = 1 ORDER BY redeemedAt DESC")
    suspend fun getRedeemedRewardsByChildId(childId: String): List<Reward>
    
    @Query("SELECT * FROM rewards WHERE childId = :childId AND isRedeemed = 0 ORDER BY pointsRequired ASC")
    suspend fun getAvailableRewardsByChildId(childId: String): List<Reward>
    
    @Query("SELECT * FROM rewards WHERE childId = :childId AND category = :category ORDER BY pointsRequired ASC")
    suspend fun getRewardsByChildIdAndCategory(childId: String, category: RewardCategory): List<Reward>
    
    @Query("SELECT * FROM rewards WHERE id = :rewardId")
    suspend fun getRewardById(rewardId: String): Reward?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReward(reward: Reward)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRewards(rewards: List<Reward>)
    
    @Update
    suspend fun updateReward(reward: Reward)
    
    @Delete
    suspend fun deleteReward(reward: Reward)
    
    @Query("DELETE FROM rewards WHERE id = :rewardId")
    suspend fun deleteRewardById(rewardId: String)
    
    @Query("UPDATE rewards SET isRedeemed = :isRedeemed, redeemedAt = :redeemedAt WHERE id = :rewardId")
    suspend fun updateRewardRedemptionStatus(rewardId: String, isRedeemed: Boolean, redeemedAt: Date?)
    
    @Query("UPDATE rewards SET isApproved = :isApproved, approvedAt = :approvedAt, approvedBy = :approvedBy WHERE id = :rewardId")
    suspend fun updateRewardApprovalStatus(rewardId: String, isApproved: Boolean, approvedAt: Date?, approvedBy: String?)
    
    @Query("UPDATE rewards SET isActive = :isActive WHERE id = :rewardId")
    suspend fun updateRewardActiveStatus(rewardId: String, isActive: Boolean)
    
    // Achievements
    @Query("SELECT * FROM achievements WHERE childId = :childId ORDER BY createdAt DESC")
    suspend fun getAchievementsByChildId(childId: String): List<Achievement>
    
    @Query("SELECT * FROM achievements WHERE childId = :childId ORDER BY createdAt DESC")
    fun getAchievementsByChildIdFlow(childId: String): Flow<List<Achievement>>
    
    @Query("SELECT * FROM achievements WHERE childId = :childId AND isUnlocked = 1 ORDER BY unlockedAt DESC")
    suspend fun getUnlockedAchievementsByChildId(childId: String): List<Achievement>
    
    @Query("SELECT * FROM achievements WHERE childId = :childId AND isUnlocked = 0 ORDER BY pointsAwarded DESC")
    suspend fun getLockedAchievementsByChildId(childId: String): List<Achievement>
    
    @Query("SELECT * FROM achievements WHERE childId = :childId AND category = :category ORDER BY createdAt DESC")
    suspend fun getAchievementsByChildIdAndCategory(childId: String, category: AchievementCategory): List<Achievement>
    
    @Query("SELECT * FROM achievements WHERE id = :achievementId")
    suspend fun getAchievementById(achievementId: String): Achievement?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievement(achievement: Achievement)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAchievements(achievements: List<Achievement>)
    
    @Update
    suspend fun updateAchievement(achievement: Achievement)
    
    @Delete
    suspend fun deleteAchievement(achievement: Achievement)
    
    @Query("DELETE FROM achievements WHERE id = :achievementId")
    suspend fun deleteAchievementById(achievementId: String)
    
    @Query("UPDATE achievements SET isUnlocked = :isUnlocked, unlockedAt = :unlockedAt WHERE id = :achievementId")
    suspend fun updateAchievementUnlockStatus(achievementId: String, isUnlocked: Boolean, unlockedAt: Date?)
    
    @Query("UPDATE achievements SET progress = :progress WHERE id = :achievementId")
    suspend fun updateAchievementProgress(achievementId: String, progress: Int)
    
    // Points
    @Query("SELECT * FROM points WHERE childId = :childId ORDER BY earnedAt DESC")
    suspend fun getPointsByChildId(childId: String): List<Points>
    
    @Query("SELECT * FROM points WHERE childId = :childId ORDER BY earnedAt DESC")
    fun getPointsByChildIdFlow(childId: String): Flow<List<Points>>
    
    @Query("SELECT * FROM points WHERE childId = :childId AND earnedAt BETWEEN :startDate AND :endDate ORDER BY earnedAt DESC")
    suspend fun getPointsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<Points>
    
    @Query("SELECT * FROM points WHERE childId = :childId AND source = :source ORDER BY earnedAt DESC")
    suspend fun getPointsByChildIdAndSource(childId: String, source: PointsSource): List<Points>
    
    @Query("SELECT * FROM points WHERE childId = :childId AND isSpent = 0 ORDER BY earnedAt DESC")
    suspend fun getAvailablePointsByChildId(childId: String): List<Points>
    
    @Query("SELECT * FROM points WHERE childId = :childId AND isSpent = 1 ORDER BY spentAt DESC")
    suspend fun getSpentPointsByChildId(childId: String): List<Points>
    
    @Query("SELECT SUM(points) FROM points WHERE childId = :childId AND isSpent = 0")
    suspend fun getTotalAvailablePointsByChildId(childId: String): Int?
    
    @Query("SELECT SUM(points) FROM points WHERE childId = :childId")
    suspend fun getTotalPointsByChildId(childId: String): Int?
    
    @Query("SELECT SUM(points) FROM points WHERE childId = :childId AND earnedAt BETWEEN :startDate AND :endDate")
    suspend fun getTotalPointsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): Int?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPoints(points: Points)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPointsList(pointsList: List<Points>)
    
    @Update
    suspend fun updatePoints(points: Points)
    
    @Delete
    suspend fun deletePoints(points: Points)
    
    @Query("DELETE FROM points WHERE id = :id")
    suspend fun deletePointsById(id: String)
    
    @Query("UPDATE points SET isSpent = :isSpent, spentAt = :spentAt, spentOn = :spentOn WHERE id = :id")
    suspend fun updatePointsSpendingStatus(id: String, isSpent: Boolean, spentAt: Date?, spentOn: String?)
    
    @Query("SELECT COUNT(*) FROM points WHERE childId = :childId AND source = :source")
    suspend fun getPointsCountByChildIdAndSource(childId: String, source: PointsSource): Int
    
    @Query("SELECT * FROM points WHERE childId = :childId AND earnedAt >= :since ORDER BY earnedAt DESC")
    suspend fun getRecentPointsByChildId(childId: String, since: Date): List<Points>
}