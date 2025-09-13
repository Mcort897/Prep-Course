package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AppUsageDao {
    
    // App Usage
    @Query("SELECT * FROM app_usage WHERE childId = :childId ORDER BY date DESC")
    suspend fun getAppUsagesByChildId(childId: String): List<AppUsage>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId ORDER BY date DESC")
    fun getAppUsagesByChildIdFlow(childId: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getAppUsagesByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<AppUsage>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND packageName = :packageName ORDER BY date DESC")
    suspend fun getAppUsagesByChildIdAndPackage(childId: String, packageName: String): List<AppUsage>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND category = :category ORDER BY date DESC")
    suspend fun getAppUsagesByChildIdAndCategory(childId: String, category: AppCategory): List<AppUsage>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND isBlocked = 1 ORDER BY date DESC")
    suspend fun getBlockedAppUsagesByChildId(childId: String): List<AppUsage>
    
    @Query("SELECT SUM(usageTime) FROM app_usage WHERE childId = :childId AND date = :date")
    suspend fun getTotalUsageTimeByChildIdAndDate(childId: String, date: Date): Long?
    
    @Query("SELECT SUM(usageTime) FROM app_usage WHERE childId = :childId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalUsageTimeByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): Long?
    
    @Query("SELECT packageName, SUM(usageTime) as totalTime FROM app_usage WHERE childId = :childId AND date BETWEEN :startDate AND :endDate GROUP BY packageName ORDER BY totalTime DESC")
    suspend fun getTopAppsByUsageTime(childId: String, startDate: Date, endDate: Date): List<AppUsageSummary>
    
    @Query("SELECT category, SUM(usageTime) as totalTime FROM app_usage WHERE childId = :childId AND date BETWEEN :startDate AND :endDate GROUP BY category ORDER BY totalTime DESC")
    suspend fun getTopCategoriesByUsageTime(childId: String, startDate: Date, endDate: Date): List<CategoryUsageSummary>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUsage(appUsage: AppUsage)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUsages(appUsages: List<AppUsage>)
    
    @Update
    suspend fun updateAppUsage(appUsage: AppUsage)
    
    @Delete
    suspend fun deleteAppUsage(appUsage: AppUsage)
    
    @Query("DELETE FROM app_usage WHERE id = :id")
    suspend fun deleteAppUsageById(id: String)
    
    @Query("DELETE FROM app_usage WHERE childId = :childId")
    suspend fun deleteAppUsagesByChildId(childId: String)
    
    @Query("DELETE FROM app_usage WHERE childId = :childId AND date < :beforeDate")
    suspend fun deleteOldAppUsages(childId: String, beforeDate: Date)
    
    // Daily Usage
    @Query("SELECT * FROM app_usage_daily WHERE childId = :childId ORDER BY date DESC")
    suspend fun getDailyUsagesByChildId(childId: String): List<AppUsageDaily>
    
    @Query("SELECT * FROM app_usage_daily WHERE childId = :childId ORDER BY date DESC")
    fun getDailyUsagesByChildIdFlow(childId: String): Flow<List<AppUsageDaily>>
    
    @Query("SELECT * FROM app_usage_daily WHERE childId = :childId AND date = :date")
    suspend fun getDailyUsageByChildIdAndDate(childId: String, date: Date): AppUsageDaily?
    
    @Query("SELECT * FROM app_usage_daily WHERE childId = :childId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getDailyUsagesByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<AppUsageDaily>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDailyUsage(dailyUsage: AppUsageDaily)
    
    @Update
    suspend fun updateDailyUsage(dailyUsage: AppUsageDaily)
    
    @Delete
    suspend fun deleteDailyUsage(dailyUsage: AppUsageDaily)
    
    // Weekly Usage
    @Query("SELECT * FROM app_usage_weekly WHERE childId = :childId ORDER BY weekStartDate DESC")
    suspend fun getWeeklyUsagesByChildId(childId: String): List<AppUsageWeekly>
    
    @Query("SELECT * FROM app_usage_weekly WHERE childId = :childId ORDER BY weekStartDate DESC")
    fun getWeeklyUsagesByChildIdFlow(childId: String): Flow<List<AppUsageWeekly>>
    
    @Query("SELECT * FROM app_usage_weekly WHERE childId = :childId AND weekStartDate <= :date AND weekEndDate >= :date")
    suspend fun getWeeklyUsageByChildIdAndDate(childId: String, date: Date): AppUsageWeekly?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyUsage(weeklyUsage: AppUsageWeekly)
    
    @Update
    suspend fun updateWeeklyUsage(weeklyUsage: AppUsageWeekly)
    
    @Delete
    suspend fun deleteWeeklyUsage(weeklyUsage: AppUsageWeekly)
    
    // Monthly Usage
    @Query("SELECT * FROM app_usage_monthly WHERE childId = :childId ORDER BY year DESC, month DESC")
    suspend fun getMonthlyUsagesByChildId(childId: String): List<AppUsageMonthly>
    
    @Query("SELECT * FROM app_usage_monthly WHERE childId = :childId ORDER BY year DESC, month DESC")
    fun getMonthlyUsagesByChildIdFlow(childId: String): Flow<List<AppUsageMonthly>>
    
    @Query("SELECT * FROM app_usage_monthly WHERE childId = :childId AND year = :year AND month = :month")
    suspend fun getMonthlyUsageByChildIdAndMonth(childId: String, year: Int, month: Int): AppUsageMonthly?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMonthlyUsage(monthlyUsage: AppUsageMonthly)
    
    @Update
    suspend fun updateMonthlyUsage(monthlyUsage: AppUsageMonthly)
    
    @Delete
    suspend fun deleteMonthlyUsage(monthlyUsage: AppUsageMonthly)
}

data class AppUsageSummary(
    val packageName: String,
    val totalTime: Long
)

data class CategoryUsageSummary(
    val category: AppCategory,
    val totalTime: Long
)