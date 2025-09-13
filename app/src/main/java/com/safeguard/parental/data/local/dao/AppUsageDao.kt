package com.safeguard.parental.data.local.dao

import androidx.room.*
import com.safeguard.parental.data.local.entity.AppUsageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppUsageDao {
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND date = :date ORDER BY usageTimeMillis DESC")
    fun getAppUsageByChildAndDate(childId: String, date: String): Flow<List<AppUsageEntity>>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId ORDER BY lastUsed DESC")
    fun getAppUsageByChild(childId: String): Flow<List<AppUsageEntity>>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND packageName = :packageName AND date = :date")
    suspend fun getAppUsageByPackageAndDate(childId: String, packageName: String, date: String): AppUsageEntity?
    
    @Query("SELECT SUM(usageTimeMillis) FROM app_usage WHERE childId = :childId AND date = :date")
    suspend fun getTotalUsageByDate(childId: String, date: String): Long?
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND date BETWEEN :startDate AND :endDate ORDER BY usageTimeMillis DESC")
    suspend fun getAppUsageByDateRange(childId: String, startDate: String, endDate: String): List<AppUsageEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUsage(appUsage: AppUsageEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppUsages(appUsages: List<AppUsageEntity>)
    
    @Update
    suspend fun updateAppUsage(appUsage: AppUsageEntity)
    
    @Delete
    suspend fun deleteAppUsage(appUsage: AppUsageEntity)
    
    @Query("DELETE FROM app_usage WHERE childId = :childId AND date < :beforeDate")
    suspend fun deleteOldAppUsage(childId: String, beforeDate: String)
    
    @Query("SELECT DISTINCT category FROM app_usage WHERE childId = :childId")
    suspend fun getAppCategoriesByChild(childId: String): List<String>
    
    @Query("SELECT * FROM app_usage WHERE childId = :childId AND category = :category AND date = :date ORDER BY usageTimeMillis DESC")
    suspend fun getAppUsageByCategory(childId: String, category: String, date: String): List<AppUsageEntity>
}