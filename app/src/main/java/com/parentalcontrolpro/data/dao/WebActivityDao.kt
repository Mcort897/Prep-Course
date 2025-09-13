package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.WebActivity
import kotlinx.coroutines.flow.Flow

@Dao
interface WebActivityDao {
    
    @Query("SELECT * FROM webactivity WHERE userId = :userId ORDER BY visitTime DESC")
    fun getWebActivityByUserId(userId: String): Flow<List<WebActivity>>
    
    @Query("SELECT * FROM webactivity WHERE userId = :userId AND visitTime BETWEEN :startDate AND :endDate ORDER BY visitTime DESC")
    fun getWebActivityByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<WebActivity>>
    
    @Query("SELECT * FROM webactivity WHERE userId = :userId AND isBlocked = 1 ORDER BY visitTime DESC")
    fun getBlockedWebActivityByUserId(userId: String): Flow<List<WebActivity>>
    
    @Query("SELECT * FROM webactivity WHERE userId = :userId AND isAllowed = 1 ORDER BY visitTime DESC")
    fun getAllowedWebActivityByUserId(userId: String): Flow<List<WebActivity>>
    
    @Query("SELECT domain, COUNT(*) as visitCount FROM webactivity WHERE userId = :userId AND visitTime BETWEEN :startDate AND :endDate GROUP BY domain ORDER BY visitCount DESC")
    suspend fun getMostVisitedSitesByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): List<WebActivitySummary>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebActivity(webActivity: WebActivity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebActivities(webActivities: List<WebActivity>)
    
    @Update
    suspend fun updateWebActivity(webActivity: WebActivity)
    
    @Delete
    suspend fun deleteWebActivity(webActivity: WebActivity)
    
    @Query("DELETE FROM webactivity WHERE userId = :userId")
    suspend fun deleteWebActivityByUserId(userId: String)
    
    data class WebActivitySummary(
        val domain: String,
        val visitCount: Int
    )
}