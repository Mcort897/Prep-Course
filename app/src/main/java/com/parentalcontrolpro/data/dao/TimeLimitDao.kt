package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.TimeLimit
import kotlinx.coroutines.flow.Flow

@Dao
interface TimeLimitDao {
    
    @Query("SELECT * FROM timelimit WHERE userId = :userId ORDER BY createdAt DESC")
    fun getTimeLimitsByUserId(userId: String): Flow<List<TimeLimit>>
    
    @Query("SELECT * FROM timelimit WHERE userId = :userId AND isActive = 1 ORDER BY createdAt DESC")
    fun getActiveTimeLimitsByUserId(userId: String): Flow<List<TimeLimit>>
    
    @Query("SELECT * FROM timelimit WHERE userId = :userId AND appPackageName = :packageName AND isActive = 1")
    suspend fun getTimeLimitByUserIdAndPackage(userId: String, packageName: String): TimeLimit?
    
    @Query("SELECT * FROM timelimit WHERE userId = :userId AND appPackageName IS NULL AND isActive = 1")
    suspend fun getGeneralTimeLimitByUserId(userId: String): TimeLimit?
    
    @Query("SELECT * FROM timelimit WHERE userId = :userId AND isExceeded = 1 ORDER BY createdAt DESC")
    fun getExceededTimeLimitsByUserId(userId: String): Flow<List<TimeLimit>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimit(timeLimit: TimeLimit)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimits(timeLimits: List<TimeLimit>)
    
    @Update
    suspend fun updateTimeLimit(timeLimit: TimeLimit)
    
    @Delete
    suspend fun deleteTimeLimit(timeLimit: TimeLimit)
    
    @Query("DELETE FROM timelimit WHERE userId = :userId")
    suspend fun deleteTimeLimitsByUserId(userId: String)
    
    @Query("UPDATE timelimit SET usedTime = :usedTime, remainingTime = :remainingTime, isExceeded = :isExceeded WHERE id = :id")
    suspend fun updateTimeLimitUsage(id: String, usedTime: Int, remainingTime: Int, isExceeded: Boolean)
    
    @Query("UPDATE timelimit SET isActive = :isActive WHERE id = :id")
    suspend fun updateTimeLimitActiveStatus(id: String, isActive: Boolean)
    
    @Query("UPDATE timelimit SET usedTime = 0, remainingTime = dailyLimit, isExceeded = 0, lastReset = :resetTime WHERE userId = :userId")
    suspend fun resetTimeLimitsForUser(userId: String, resetTime: Long)
}