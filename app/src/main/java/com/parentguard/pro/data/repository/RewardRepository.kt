package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.RewardDao
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RewardRepository @Inject constructor(
    private val rewardDao: RewardDao
) {
    
    // Rewards
    suspend fun getRewardsByChildId(childId: String): List<Reward> = 
        rewardDao.getRewardsByChildId(childId)
    
    fun getRewardsByChildIdFlow(childId: String): Flow<List<Reward>> = 
        rewardDao.getRewardsByChildIdFlow(childId)
    
    suspend fun getActiveRewardsByChildId(childId: String): List<Reward> = 
        rewardDao.getActiveRewardsByChildId(childId)
    
    fun getActiveRewardsByChildIdFlow(childId: String): Flow<List<Reward>> = 
        rewardDao.getActiveRewardsByChildIdFlow(childId)
    
    suspend fun getRedeemedRewardsByChildId(childId: String): List<Reward> = 
        rewardDao.getRedeemedRewardsByChildId(childId)
    
    suspend fun getAvailableRewardsByChildId(childId: String): List<Reward> = 
        rewardDao.getAvailableRewardsByChildId(childId)
    
    suspend fun getRewardsByChildIdAndCategory(childId: String, category: RewardCategory): List<Reward> = 
        rewardDao.getRewardsByChildIdAndCategory(childId, category)
    
    suspend fun getRewardById(rewardId: String): Reward? = 
        rewardDao.getRewardById(rewardId)
    
    suspend fun insertReward(reward: Reward) = rewardDao.insertReward(reward)
    
    suspend fun insertRewards(rewards: List<Reward>) = rewardDao.insertRewards(rewards)
    
    suspend fun updateReward(reward: Reward) = rewardDao.updateReward(reward)
    
    suspend fun deleteReward(reward: Reward) = rewardDao.deleteReward(reward)
    
    suspend fun deleteRewardById(rewardId: String) = rewardDao.deleteRewardById(rewardId)
    
    suspend fun updateRewardRedemptionStatus(rewardId: String, isRedeemed: Boolean, redeemedAt: Date?) = 
        rewardDao.updateRewardRedemptionStatus(rewardId, isRedeemed, redeemedAt)
    
    suspend fun updateRewardApprovalStatus(rewardId: String, isApproved: Boolean, approvedAt: Date?, approvedBy: String?) = 
        rewardDao.updateRewardApprovalStatus(rewardId, isApproved, approvedAt, approvedBy)
    
    suspend fun updateRewardActiveStatus(rewardId: String, isActive: Boolean) = 
        rewardDao.updateRewardActiveStatus(rewardId, isActive)
    
    // Achievements
    suspend fun getAchievementsByChildId(childId: String): List<Achievement> = 
        rewardDao.getAchievementsByChildId(childId)
    
    fun getAchievementsByChildIdFlow(childId: String): Flow<List<Achievement>> = 
        rewardDao.getAchievementsByChildIdFlow(childId)
    
    suspend fun getUnlockedAchievementsByChildId(childId: String): List<Achievement> = 
        rewardDao.getUnlockedAchievementsByChildId(childId)
    
    suspend fun getLockedAchievementsByChildId(childId: String): List<Achievement> = 
        rewardDao.getLockedAchievementsByChildId(childId)
    
    suspend fun getAchievementsByChildIdAndCategory(childId: String, category: AchievementCategory): List<Achievement> = 
        rewardDao.getAchievementsByChildIdAndCategory(childId, category)
    
    suspend fun getAchievementById(achievementId: String): Achievement? = 
        rewardDao.getAchievementById(achievementId)
    
    suspend fun insertAchievement(achievement: Achievement) = rewardDao.insertAchievement(achievement)
    
    suspend fun insertAchievements(achievements: List<Achievement>) = rewardDao.insertAchievements(achievements)
    
    suspend fun updateAchievement(achievement: Achievement) = rewardDao.updateAchievement(achievement)
    
    suspend fun deleteAchievement(achievement: Achievement) = rewardDao.deleteAchievement(achievement)
    
    suspend fun deleteAchievementById(achievementId: String) = rewardDao.deleteAchievementById(achievementId)
    
    suspend fun updateAchievementUnlockStatus(achievementId: String, isUnlocked: Boolean, unlockedAt: Date?) = 
        rewardDao.updateAchievementUnlockStatus(achievementId, isUnlocked, unlockedAt)
    
    suspend fun updateAchievementProgress(achievementId: String, progress: Int) = 
        rewardDao.updateAchievementProgress(achievementId, progress)
    
    // Points
    suspend fun getPointsByChildId(childId: String): List<Points> = 
        rewardDao.getPointsByChildId(childId)
    
    fun getPointsByChildIdFlow(childId: String): Flow<List<Points>> = 
        rewardDao.getPointsByChildIdFlow(childId)
    
    suspend fun getPointsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<Points> = 
        rewardDao.getPointsByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun getPointsByChildIdAndSource(childId: String, source: PointsSource): List<Points> = 
        rewardDao.getPointsByChildIdAndSource(childId, source)
    
    suspend fun getAvailablePointsByChildId(childId: String): List<Points> = 
        rewardDao.getAvailablePointsByChildId(childId)
    
    suspend fun getSpentPointsByChildId(childId: String): List<Points> = 
        rewardDao.getSpentPointsByChildId(childId)
    
    suspend fun getTotalAvailablePointsByChildId(childId: String): Int? = 
        rewardDao.getTotalAvailablePointsByChildId(childId)
    
    suspend fun getTotalPointsByChildId(childId: String): Int? = 
        rewardDao.getTotalPointsByChildId(childId)
    
    suspend fun getTotalPointsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): Int? = 
        rewardDao.getTotalPointsByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun insertPoints(points: Points) = rewardDao.insertPoints(points)
    
    suspend fun insertPointsList(pointsList: List<Points>) = rewardDao.insertPointsList(pointsList)
    
    suspend fun updatePoints(points: Points) = rewardDao.updatePoints(points)
    
    suspend fun deletePoints(points: Points) = rewardDao.deletePoints(points)
    
    suspend fun deletePointsById(id: String) = rewardDao.deletePointsById(id)
    
    suspend fun updatePointsSpendingStatus(id: String, isSpent: Boolean, spentAt: Date?, spentOn: String?) = 
        rewardDao.updatePointsSpendingStatus(id, isSpent, spentAt, spentOn)
    
    suspend fun getPointsCountByChildIdAndSource(childId: String, source: PointsSource): Int = 
        rewardDao.getPointsCountByChildIdAndSource(childId, source)
    
    suspend fun getRecentPointsByChildId(childId: String, since: Date): List<Points> = 
        rewardDao.getRecentPointsByChildId(childId, since)
}