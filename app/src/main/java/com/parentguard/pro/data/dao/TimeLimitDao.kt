package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TimeLimitDao {
    
    // Time Limits
    @Query("SELECT * FROM time_limits WHERE childId = :childId ORDER BY createdAt DESC")
    suspend fun getTimeLimitsByChildId(childId: String): List<TimeLimit>
    
    @Query("SELECT * FROM time_limits WHERE childId = :childId ORDER BY createdAt DESC")
    fun getTimeLimitsByChildIdFlow(childId: String): Flow<List<TimeLimit>>
    
    @Query("SELECT * FROM time_limits WHERE childId = :childId AND isActive = 1 ORDER BY createdAt DESC")
    suspend fun getActiveTimeLimitsByChildId(childId: String): List<TimeLimit>
    
    @Query("SELECT * FROM time_limits WHERE childId = :childId AND isActive = 1 ORDER BY createdAt DESC")
    fun getActiveTimeLimitsByChildIdFlow(childId: String): Flow<List<TimeLimit>>
    
    @Query("SELECT * FROM time_limits WHERE childId = :childId AND type = :type ORDER BY createdAt DESC")
    suspend fun getTimeLimitsByChildIdAndType(childId: String, type: TimeLimitType): List<TimeLimit>
    
    @Query("SELECT * FROM time_limits WHERE id = :timeLimitId")
    suspend fun getTimeLimitById(timeLimitId: String): TimeLimit?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimit(timeLimit: TimeLimit)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimits(timeLimits: List<TimeLimit>)
    
    @Update
    suspend fun updateTimeLimit(timeLimit: TimeLimit)
    
    @Delete
    suspend fun deleteTimeLimit(timeLimit: TimeLimit)
    
    @Query("DELETE FROM time_limits WHERE id = :timeLimitId")
    suspend fun deleteTimeLimitById(timeLimitId: String)
    
    @Query("UPDATE time_limits SET isActive = :isActive WHERE id = :timeLimitId")
    suspend fun updateTimeLimitActiveStatus(timeLimitId: String, isActive: Boolean)
    
    @Query("UPDATE time_limits SET lastModifiedAt = :lastModifiedAt WHERE id = :timeLimitId")
    suspend fun updateTimeLimitLastModified(timeLimitId: String, lastModifiedAt: Date)
    
    // Time Limit Usage
    @Query("SELECT * FROM time_limit_usage WHERE childId = :childId ORDER BY date DESC")
    suspend fun getTimeLimitUsagesByChildId(childId: String): List<TimeLimitUsage>
    
    @Query("SELECT * FROM time_limit_usage WHERE childId = :childId ORDER BY date DESC")
    fun getTimeLimitUsagesByChildIdFlow(childId: String): Flow<List<TimeLimitUsage>>
    
    @Query("SELECT * FROM time_limit_usage WHERE timeLimitId = :timeLimitId ORDER BY date DESC")
    suspend fun getTimeLimitUsagesByTimeLimitId(timeLimitId: String): List<TimeLimitUsage>
    
    @Query("SELECT * FROM time_limit_usage WHERE childId = :childId AND date = :date")
    suspend fun getTimeLimitUsageByChildIdAndDate(childId: String, date: Date): List<TimeLimitUsage>
    
    @Query("SELECT * FROM time_limit_usage WHERE childId = :childId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getTimeLimitUsagesByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<TimeLimitUsage>
    
    @Query("SELECT * FROM time_limit_usage WHERE timeLimitId = :timeLimitId AND date = :date")
    suspend fun getTimeLimitUsageByTimeLimitIdAndDate(timeLimitId: String, date: Date): TimeLimitUsage?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimitUsage(timeLimitUsage: TimeLimitUsage)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimitUsages(timeLimitUsages: List<TimeLimitUsage>)
    
    @Update
    suspend fun updateTimeLimitUsage(timeLimitUsage: TimeLimitUsage)
    
    @Delete
    suspend fun deleteTimeLimitUsage(timeLimitUsage: TimeLimitUsage)
    
    @Query("DELETE FROM time_limit_usage WHERE id = :id")
    suspend fun deleteTimeLimitUsageById(id: String)
    
    @Query("UPDATE time_limit_usage SET usedTime = :usedTime, remainingTime = :remainingTime, isExceeded = :isExceeded, exceededBy = :exceededBy WHERE id = :id")
    suspend fun updateTimeLimitUsageStats(id: String, usedTime: Long, remainingTime: Long, isExceeded: Boolean, exceededBy: Long)
    
    @Query("UPDATE time_limit_usage SET extensionsUsed = :extensionsUsed WHERE id = :id")
    suspend fun updateTimeLimitUsageExtensions(id: String, extensionsUsed: Int)
    
    @Query("UPDATE time_limit_usage SET overridesUsed = :overridesUsed WHERE id = :id")
    suspend fun updateTimeLimitUsageOverrides(id: String, overridesUsed: Int)
    
    @Query("UPDATE time_limit_usage SET warningsSent = :warningsSent, lastWarningAt = :lastWarningAt WHERE id = :id")
    suspend fun updateTimeLimitUsageWarnings(id: String, warningsSent: Int, lastWarningAt: Date)
    
    // Time Limit Violations
    @Query("SELECT * FROM time_limit_violations WHERE childId = :childId ORDER BY violatedAt DESC")
    suspend fun getTimeLimitViolationsByChildId(childId: String): List<TimeLimitViolation>
    
    @Query("SELECT * FROM time_limit_violations WHERE childId = :childId ORDER BY violatedAt DESC")
    fun getTimeLimitViolationsByChildIdFlow(childId: String): Flow<List<TimeLimitViolation>>
    
    @Query("SELECT * FROM time_limit_violations WHERE timeLimitId = :timeLimitId ORDER BY violatedAt DESC")
    suspend fun getTimeLimitViolationsByTimeLimitId(timeLimitId: String): List<TimeLimitViolation>
    
    @Query("SELECT * FROM time_limit_violations WHERE childId = :childId AND isResolved = 0 ORDER BY violatedAt DESC")
    suspend fun getUnresolvedTimeLimitViolationsByChildId(childId: String): List<TimeLimitViolation>
    
    @Query("SELECT * FROM time_limit_violations WHERE childId = :childId AND violationType = :violationType ORDER BY violatedAt DESC")
    suspend fun getTimeLimitViolationsByChildIdAndType(childId: String, violationType: ViolationType): List<TimeLimitViolation>
    
    @Query("SELECT * FROM time_limit_violations WHERE childId = :childId AND violatedAt BETWEEN :startDate AND :endDate ORDER BY violatedAt DESC")
    suspend fun getTimeLimitViolationsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<TimeLimitViolation>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimitViolation(violation: TimeLimitViolation)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTimeLimitViolations(violations: List<TimeLimitViolation>)
    
    @Update
    suspend fun updateTimeLimitViolation(violation: TimeLimitViolation)
    
    @Delete
    suspend fun deleteTimeLimitViolation(violation: TimeLimitViolation)
    
    @Query("DELETE FROM time_limit_violations WHERE id = :id")
    suspend fun deleteTimeLimitViolationById(id: String)
    
    @Query("UPDATE time_limit_violations SET isResolved = :isResolved, resolvedAt = :resolvedAt, resolutionType = :resolutionType, notes = :notes WHERE id = :id")
    suspend fun resolveTimeLimitViolation(id: String, isResolved: Boolean, resolvedAt: Date?, resolutionType: ResolutionType?, notes: String?)
    
    @Query("SELECT COUNT(*) FROM time_limit_violations WHERE childId = :childId AND isResolved = 0")
    suspend fun getUnresolvedViolationCountByChildId(childId: String): Int
    
    @Query("SELECT COUNT(*) FROM time_limit_violations WHERE childId = :childId AND violationType = :violationType")
    suspend fun getViolationCountByChildIdAndType(childId: String, violationType: ViolationType): Int
}