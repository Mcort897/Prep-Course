package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.TimeLimitDao
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeLimitRepository @Inject constructor(
    private val timeLimitDao: TimeLimitDao
) {
    
    // Time Limits
    suspend fun getTimeLimitsByChildId(childId: String): List<TimeLimit> = 
        timeLimitDao.getTimeLimitsByChildId(childId)
    
    fun getTimeLimitsByChildIdFlow(childId: String): Flow<List<TimeLimit>> = 
        timeLimitDao.getTimeLimitsByChildIdFlow(childId)
    
    suspend fun getActiveTimeLimitsByChildId(childId: String): List<TimeLimit> = 
        timeLimitDao.getActiveTimeLimitsByChildId(childId)
    
    fun getActiveTimeLimitsByChildIdFlow(childId: String): Flow<List<TimeLimit>> = 
        timeLimitDao.getActiveTimeLimitsByChildIdFlow(childId)
    
    suspend fun getTimeLimitsByChildIdAndType(childId: String, type: TimeLimitType): List<TimeLimit> = 
        timeLimitDao.getTimeLimitsByChildIdAndType(childId, type)
    
    suspend fun getTimeLimitById(timeLimitId: String): TimeLimit? = 
        timeLimitDao.getTimeLimitById(timeLimitId)
    
    suspend fun insertTimeLimit(timeLimit: TimeLimit) = timeLimitDao.insertTimeLimit(timeLimit)
    
    suspend fun insertTimeLimits(timeLimits: List<TimeLimit>) = timeLimitDao.insertTimeLimits(timeLimits)
    
    suspend fun updateTimeLimit(timeLimit: TimeLimit) = timeLimitDao.updateTimeLimit(timeLimit)
    
    suspend fun deleteTimeLimit(timeLimit: TimeLimit) = timeLimitDao.deleteTimeLimit(timeLimit)
    
    suspend fun deleteTimeLimitById(timeLimitId: String) = timeLimitDao.deleteTimeLimitById(timeLimitId)
    
    suspend fun updateTimeLimitActiveStatus(timeLimitId: String, isActive: Boolean) = 
        timeLimitDao.updateTimeLimitActiveStatus(timeLimitId, isActive)
    
    suspend fun updateTimeLimitLastModified(timeLimitId: String, lastModifiedAt: Date) = 
        timeLimitDao.updateTimeLimitLastModified(timeLimitId, lastModifiedAt)
    
    // Time Limit Usage
    suspend fun getTimeLimitUsagesByChildId(childId: String): List<TimeLimitUsage> = 
        timeLimitDao.getTimeLimitUsagesByChildId(childId)
    
    fun getTimeLimitUsagesByChildIdFlow(childId: String): Flow<List<TimeLimitUsage>> = 
        timeLimitDao.getTimeLimitUsagesByChildIdFlow(childId)
    
    suspend fun getTimeLimitUsagesByTimeLimitId(timeLimitId: String): List<TimeLimitUsage> = 
        timeLimitDao.getTimeLimitUsagesByTimeLimitId(timeLimitId)
    
    suspend fun getTimeLimitUsageByChildIdAndDate(childId: String, date: Date): List<TimeLimitUsage> = 
        timeLimitDao.getTimeLimitUsageByChildIdAndDate(childId, date)
    
    suspend fun getTimeLimitUsagesByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<TimeLimitUsage> = 
        timeLimitDao.getTimeLimitUsagesByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun getTimeLimitUsageByTimeLimitIdAndDate(timeLimitId: String, date: Date): TimeLimitUsage? = 
        timeLimitDao.getTimeLimitUsageByTimeLimitIdAndDate(timeLimitId, date)
    
    suspend fun insertTimeLimitUsage(timeLimitUsage: TimeLimitUsage) = timeLimitDao.insertTimeLimitUsage(timeLimitUsage)
    
    suspend fun insertTimeLimitUsages(timeLimitUsages: List<TimeLimitUsage>) = timeLimitDao.insertTimeLimitUsages(timeLimitUsages)
    
    suspend fun updateTimeLimitUsage(timeLimitUsage: TimeLimitUsage) = timeLimitDao.updateTimeLimitUsage(timeLimitUsage)
    
    suspend fun deleteTimeLimitUsage(timeLimitUsage: TimeLimitUsage) = timeLimitDao.deleteTimeLimitUsage(timeLimitUsage)
    
    suspend fun deleteTimeLimitUsageById(id: String) = timeLimitDao.deleteTimeLimitUsageById(id)
    
    suspend fun updateTimeLimitUsageStats(id: String, usedTime: Long, remainingTime: Long, isExceeded: Boolean, exceededBy: Long) = 
        timeLimitDao.updateTimeLimitUsageStats(id, usedTime, remainingTime, isExceeded, exceededBy)
    
    suspend fun updateTimeLimitUsageExtensions(id: String, extensionsUsed: Int) = 
        timeLimitDao.updateTimeLimitUsageExtensions(id, extensionsUsed)
    
    suspend fun updateTimeLimitUsageOverrides(id: String, overridesUsed: Int) = 
        timeLimitDao.updateTimeLimitUsageOverrides(id, overridesUsed)
    
    suspend fun updateTimeLimitUsageWarnings(id: String, warningsSent: Int, lastWarningAt: Date) = 
        timeLimitDao.updateTimeLimitUsageWarnings(id, warningsSent, lastWarningAt)
    
    // Time Limit Violations
    suspend fun getTimeLimitViolationsByChildId(childId: String): List<TimeLimitViolation> = 
        timeLimitDao.getTimeLimitViolationsByChildId(childId)
    
    fun getTimeLimitViolationsByChildIdFlow(childId: String): Flow<List<TimeLimitViolation>> = 
        timeLimitDao.getTimeLimitViolationsByChildIdFlow(childId)
    
    suspend fun getTimeLimitViolationsByTimeLimitId(timeLimitId: String): List<TimeLimitViolation> = 
        timeLimitDao.getTimeLimitViolationsByTimeLimitId(timeLimitId)
    
    suspend fun getUnresolvedTimeLimitViolationsByChildId(childId: String): List<TimeLimitViolation> = 
        timeLimitDao.getUnresolvedTimeLimitViolationsByChildId(childId)
    
    suspend fun getTimeLimitViolationsByChildIdAndType(childId: String, violationType: ViolationType): List<TimeLimitViolation> = 
        timeLimitDao.getTimeLimitViolationsByChildIdAndType(childId, violationType)
    
    suspend fun getTimeLimitViolationsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<TimeLimitViolation> = 
        timeLimitDao.getTimeLimitViolationsByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun insertTimeLimitViolation(violation: TimeLimitViolation) = timeLimitDao.insertTimeLimitViolation(violation)
    
    suspend fun insertTimeLimitViolations(violations: List<TimeLimitViolation>) = timeLimitDao.insertTimeLimitViolations(violations)
    
    suspend fun updateTimeLimitViolation(violation: TimeLimitViolation) = timeLimitDao.updateTimeLimitViolation(violation)
    
    suspend fun deleteTimeLimitViolation(violation: TimeLimitViolation) = timeLimitDao.deleteTimeLimitViolation(violation)
    
    suspend fun deleteTimeLimitViolationById(id: String) = timeLimitDao.deleteTimeLimitViolationById(id)
    
    suspend fun resolveTimeLimitViolation(id: String, isResolved: Boolean, resolvedAt: Date?, resolutionType: ResolutionType?, notes: String?) = 
        timeLimitDao.resolveTimeLimitViolation(id, isResolved, resolvedAt, resolutionType, notes)
    
    suspend fun getUnresolvedViolationCountByChildId(childId: String): Int = 
        timeLimitDao.getUnresolvedViolationCountByChildId(childId)
    
    suspend fun getViolationCountByChildIdAndType(childId: String, violationType: ViolationType): Int = 
        timeLimitDao.getViolationCountByChildIdAndType(childId, violationType)
}