package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.AppUsageDao
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUsageRepository @Inject constructor(
    private val appUsageDao: AppUsageDao
) {
    
    // App Usage
    suspend fun getAppUsagesByChildId(childId: String): List<AppUsage> = 
        appUsageDao.getAppUsagesByChildId(childId)
    
    fun getAppUsagesByChildIdFlow(childId: String): Flow<List<AppUsage>> = 
        appUsageDao.getAppUsagesByChildIdFlow(childId)
    
    suspend fun getAppUsagesByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<AppUsage> = 
        appUsageDao.getAppUsagesByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun getAppUsagesByChildIdAndPackage(childId: String, packageName: String): List<AppUsage> = 
        appUsageDao.getAppUsagesByChildIdAndPackage(childId, packageName)
    
    suspend fun getAppUsagesByChildIdAndCategory(childId: String, category: AppCategory): List<AppUsage> = 
        appUsageDao.getAppUsagesByChildIdAndCategory(childId, category)
    
    suspend fun getBlockedAppUsagesByChildId(childId: String): List<AppUsage> = 
        appUsageDao.getBlockedAppUsagesByChildId(childId)
    
    suspend fun getTotalUsageTimeByChildIdAndDate(childId: String, date: Date): Long? = 
        appUsageDao.getTotalUsageTimeByChildIdAndDate(childId, date)
    
    suspend fun getTotalUsageTimeByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): Long? = 
        appUsageDao.getTotalUsageTimeByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun getTopAppsByUsageTime(childId: String, startDate: Date, endDate: Date): List<AppUsageSummary> = 
        appUsageDao.getTopAppsByUsageTime(childId, startDate, endDate)
    
    suspend fun getTopCategoriesByUsageTime(childId: String, startDate: Date, endDate: Date): List<CategoryUsageSummary> = 
        appUsageDao.getTopCategoriesByUsageTime(childId, startDate, endDate)
    
    suspend fun insertAppUsage(appUsage: AppUsage) = appUsageDao.insertAppUsage(appUsage)
    
    suspend fun insertAppUsages(appUsages: List<AppUsage>) = appUsageDao.insertAppUsages(appUsages)
    
    suspend fun updateAppUsage(appUsage: AppUsage) = appUsageDao.updateAppUsage(appUsage)
    
    suspend fun deleteAppUsage(appUsage: AppUsage) = appUsageDao.deleteAppUsage(appUsage)
    
    suspend fun deleteAppUsageById(id: String) = appUsageDao.deleteAppUsageById(id)
    
    suspend fun deleteAppUsagesByChildId(childId: String) = appUsageDao.deleteAppUsagesByChildId(childId)
    
    suspend fun deleteOldAppUsages(childId: String, beforeDate: Date) = 
        appUsageDao.deleteOldAppUsages(childId, beforeDate)
    
    // Daily Usage
    suspend fun getDailyUsagesByChildId(childId: String): List<AppUsageDaily> = 
        appUsageDao.getDailyUsagesByChildId(childId)
    
    fun getDailyUsagesByChildIdFlow(childId: String): Flow<List<AppUsageDaily>> = 
        appUsageDao.getDailyUsagesByChildIdFlow(childId)
    
    suspend fun getDailyUsageByChildIdAndDate(childId: String, date: Date): AppUsageDaily? = 
        appUsageDao.getDailyUsageByChildIdAndDate(childId, date)
    
    suspend fun getDailyUsagesByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<AppUsageDaily> = 
        appUsageDao.getDailyUsagesByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun insertDailyUsage(dailyUsage: AppUsageDaily) = appUsageDao.insertDailyUsage(dailyUsage)
    
    suspend fun updateDailyUsage(dailyUsage: AppUsageDaily) = appUsageDao.updateDailyUsage(dailyUsage)
    
    suspend fun deleteDailyUsage(dailyUsage: AppUsageDaily) = appUsageDao.deleteDailyUsage(dailyUsage)
    
    // Weekly Usage
    suspend fun getWeeklyUsagesByChildId(childId: String): List<AppUsageWeekly> = 
        appUsageDao.getWeeklyUsagesByChildId(childId)
    
    fun getWeeklyUsagesByChildIdFlow(childId: String): Flow<List<AppUsageWeekly>> = 
        appUsageDao.getWeeklyUsagesByChildIdFlow(childId)
    
    suspend fun getWeeklyUsageByChildIdAndDate(childId: String, date: Date): AppUsageWeekly? = 
        appUsageDao.getWeeklyUsageByChildIdAndDate(childId, date)
    
    suspend fun insertWeeklyUsage(weeklyUsage: AppUsageWeekly) = appUsageDao.insertWeeklyUsage(weeklyUsage)
    
    suspend fun updateWeeklyUsage(weeklyUsage: AppUsageWeekly) = appUsageDao.updateWeeklyUsage(weeklyUsage)
    
    suspend fun deleteWeeklyUsage(weeklyUsage: AppUsageWeekly) = appUsageDao.deleteWeeklyUsage(weeklyUsage)
    
    // Monthly Usage
    suspend fun getMonthlyUsagesByChildId(childId: String): List<AppUsageMonthly> = 
        appUsageDao.getMonthlyUsagesByChildId(childId)
    
    fun getMonthlyUsagesByChildIdFlow(childId: String): Flow<List<AppUsageMonthly>> = 
        appUsageDao.getMonthlyUsagesByChildIdFlow(childId)
    
    suspend fun getMonthlyUsageByChildIdAndMonth(childId: String, year: Int, month: Int): AppUsageMonthly? = 
        appUsageDao.getMonthlyUsageByChildIdAndMonth(childId, year, month)
    
    suspend fun insertMonthlyUsage(monthlyUsage: AppUsageMonthly) = appUsageDao.insertMonthlyUsage(monthlyUsage)
    
    suspend fun updateMonthlyUsage(monthlyUsage: AppUsageMonthly) = appUsageDao.updateMonthlyUsage(monthlyUsage)
    
    suspend fun deleteMonthlyUsage(monthlyUsage: AppUsageMonthly) = appUsageDao.deleteMonthlyUsage(monthlyUsage)
}