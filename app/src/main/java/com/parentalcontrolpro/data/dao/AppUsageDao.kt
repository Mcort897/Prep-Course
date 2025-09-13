package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.AppUsage
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface AppUsageDao {
    
    @Query("SELECT * FROM appusage WHERE userId = :userId ORDER BY date DESC")
    fun getAppUsageByUserId(userId: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM appusage WHERE userId = :userId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    fun getAppUsageByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM appusage WHERE userId = :userId AND packageName = :packageName ORDER BY date DESC")
    fun getAppUsageByUserIdAndPackage(userId: String, packageName: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM appusage WHERE userId = :userId AND date = :date")
    suspend fun getAppUsageByUserIdAndDate(userId: String, date: Long): List<AppUsage>
    
    @Query("SELECT * FROM appusage WHERE userId = :userId AND isBlocked = 1 ORDER BY date DESC")
    fun getBlockedAppUsageByUserId(userId: String): Flow<List<AppUsage>>
    
    @Query("SELECT * FROM appusage WHERE userId = :userId AND isAllowed = 1 ORDER BY date DESC")
    fun getAllowedAppUsageByUserId(userId: String): Flow<List<AppUsage>>
    
    @Query("SELECT packageName, SUM(usageTime) as totalTime FROM appusage WHERE userId = :userId AND date BETWEEN :startDate AND :endDate GROUP BY packageName ORDER BY totalTime DESC")
    suspend fun getMostUsedAppsByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): List<AppUsageSummary>
    
    @Query("SELECT packageName, SUM(usageTime) as totalTime FROM appusage WHERE userId = :userId AND date = :date GROUP BY packageName ORDER BY totalTime DESC")
    suspend fun getMostUsedAppsByUserIdAndDate(userId: String, date: Long): List<AppUsageSummary>
    
    @Query("SELECT SUM(usageTime) FROM appusage WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getTotalUsageTimeByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Long
    
    @Query("SELECT SUM(usageTime) FROM appusage WHERE userId = :userId AND date = :date")
    suspend fun getTotalUsageTimeByUserIdAndDate(userId: String, date: Long): Long
    
    @Query("SELECT COUNT(DISTINCT packageName) FROM appusage WHERE userId = :userId AND date BETWEEN :startDate AND :endDate")
    suspend fun getUniqueAppCountByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUsage(appUsage: AppUsage)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUsages(appUsages: List<AppUsage>)
    
    @Update
    suspend fun updateAppUsage(appUsage: AppUsage)
    
    @Delete
    suspend fun deleteAppUsage(appUsage: AppUsage)
    
    @Query("DELETE FROM appusage WHERE userId = :userId")
    suspend fun deleteAppUsageByUserId(userId: String)
    
    @Query("DELETE FROM appusage WHERE userId = :userId AND date < :date")
    suspend fun deleteOldAppUsageByUserId(userId: String, date: Long)
    
    @Query("UPDATE appusage SET isBlocked = :isBlocked WHERE userId = :userId AND packageName = :packageName")
    suspend fun updateAppBlockedStatus(userId: String, packageName: String, isBlocked: Boolean)
    
    @Query("UPDATE appusage SET isAllowed = :isAllowed WHERE userId = :userId AND packageName = :packageName")
    suspend fun updateAppAllowedStatus(userId: String, packageName: String, isAllowed: Boolean)
    
    data class AppUsageSummary(
        val packageName: String,
        val totalTime: Long
    )
}