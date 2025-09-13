package com.parentalcontrolpro.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.parentalcontrolpro.data.dao.AppUsageDao
import com.parentalcontrolpro.data.model.AppUsage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppUsageRepository @Inject constructor(
    private val appUsageDao: AppUsageDao,
    private val firestore: FirebaseFirestore
) {
    
    fun getAppUsageByUserId(userId: String): Flow<List<AppUsage>> {
        return appUsageDao.getAppUsageByUserId(userId)
    }
    
    fun getAppUsageByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<AppUsage>> {
        return appUsageDao.getAppUsageByUserIdAndDateRange(userId, startDate, endDate)
    }
    
    fun getAppUsageByUserIdAndPackage(userId: String, packageName: String): Flow<List<AppUsage>> {
        return appUsageDao.getAppUsageByUserIdAndPackage(userId, packageName)
    }
    
    suspend fun getAppUsageByUserIdAndDate(userId: String, date: Long): List<AppUsage> {
        return appUsageDao.getAppUsageByUserIdAndDate(userId, date)
    }
    
    fun getBlockedAppUsageByUserId(userId: String): Flow<List<AppUsage>> {
        return appUsageDao.getBlockedAppUsageByUserId(userId)
    }
    
    fun getAllowedAppUsageByUserId(userId: String): Flow<List<AppUsage>> {
        return appUsageDao.getAllowedAppUsageByUserId(userId)
    }
    
    suspend fun getMostUsedAppsByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): List<AppUsageDao.AppUsageSummary> {
        return appUsageDao.getMostUsedAppsByUserIdAndDateRange(userId, startDate, endDate)
    }
    
    suspend fun getMostUsedAppsByUserIdAndDate(userId: String, date: Long): List<AppUsageDao.AppUsageSummary> {
        return appUsageDao.getMostUsedAppsByUserIdAndDate(userId, date)
    }
    
    suspend fun getTotalUsageTimeByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Long {
        return appUsageDao.getTotalUsageTimeByUserIdAndDateRange(userId, startDate, endDate)
    }
    
    suspend fun getTotalUsageTimeByUserIdAndDate(userId: String, date: Long): Long {
        return appUsageDao.getTotalUsageTimeByUserIdAndDate(userId, date)
    }
    
    suspend fun getUniqueAppCountByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Int {
        return appUsageDao.getUniqueAppCountByUserIdAndDateRange(userId, startDate, endDate)
    }
    
    suspend fun insertAppUsage(appUsage: AppUsage) {
        appUsageDao.insertAppUsage(appUsage)
        // Sync with Firebase
        syncAppUsageToFirebase(appUsage)
    }
    
    suspend fun insertAppUsages(appUsages: List<AppUsage>) {
        appUsageDao.insertAppUsages(appUsages)
        // Sync with Firebase
        appUsages.forEach { syncAppUsageToFirebase(it) }
    }
    
    suspend fun updateAppUsage(appUsage: AppUsage) {
        appUsageDao.updateAppUsage(appUsage)
        // Sync with Firebase
        syncAppUsageToFirebase(appUsage)
    }
    
    suspend fun deleteAppUsage(appUsage: AppUsage) {
        appUsageDao.deleteAppUsage(appUsage)
        // Sync with Firebase
        deleteAppUsageFromFirebase(appUsage.id)
    }
    
    suspend fun deleteAppUsageByUserId(userId: String) {
        appUsageDao.deleteAppUsageByUserId(userId)
        // Sync with Firebase
        deleteAppUsageFromFirebaseByUserId(userId)
    }
    
    suspend fun updateAppBlockedStatus(userId: String, packageName: String, isBlocked: Boolean) {
        appUsageDao.updateAppBlockedStatus(userId, packageName, isBlocked)
        // Sync with Firebase
        updateAppBlockedStatusInFirebase(userId, packageName, isBlocked)
    }
    
    suspend fun updateAppAllowedStatus(userId: String, packageName: String, isAllowed: Boolean) {
        appUsageDao.updateAppAllowedStatus(userId, packageName, isAllowed)
        // Sync with Firebase
        updateAppAllowedStatusInFirebase(userId, packageName, isAllowed)
    }
    
    private suspend fun syncAppUsageToFirebase(appUsage: AppUsage) {
        try {
            firestore.collection("app_usage")
                .document(appUsage.id)
                .set(appUsage)
                .await()
        } catch (e: Exception) {
            // Handle error - could be logged or retried
        }
    }
    
    private suspend fun deleteAppUsageFromFirebase(appUsageId: String) {
        try {
            firestore.collection("app_usage")
                .document(appUsageId)
                .delete()
                .await()
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    private suspend fun deleteAppUsageFromFirebaseByUserId(userId: String) {
        try {
            val snapshot = firestore.collection("app_usage")
                .whereEqualTo("userId", userId)
                .get().await()
            
            val batch = firestore.batch()
            snapshot.documents.forEach { document ->
                batch.delete(document.reference)
            }
            batch.commit().await()
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    private suspend fun updateAppBlockedStatusInFirebase(userId: String, packageName: String, isBlocked: Boolean) {
        try {
            val snapshot = firestore.collection("app_usage")
                .whereEqualTo("userId", userId)
                .whereEqualTo("packageName", packageName)
                .get().await()
            
            val batch = firestore.batch()
            snapshot.documents.forEach { document ->
                batch.update(document.reference, "isBlocked", isBlocked)
            }
            batch.commit().await()
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    private suspend fun updateAppAllowedStatusInFirebase(userId: String, packageName: String, isAllowed: Boolean) {
        try {
            val snapshot = firestore.collection("app_usage")
                .whereEqualTo("userId", userId)
                .whereEqualTo("packageName", packageName)
                .get().await()
            
            val batch = firestore.batch()
            snapshot.documents.forEach { document ->
                batch.update(document.reference, "isAllowed", isAllowed)
            }
            batch.commit().await()
        } catch (e: Exception) {
            // Handle error
        }
    }
    
    suspend fun syncAppUsageFromFirebase(userId: String) {
        try {
            val snapshot = firestore.collection("app_usage")
                .whereEqualTo("userId", userId)
                .get().await()
            
            val appUsages = snapshot.documents.mapNotNull { document ->
                document.toObject(AppUsage::class.java)
            }
            
            if (appUsages.isNotEmpty()) {
                appUsageDao.insertAppUsages(appUsages)
            }
        } catch (e: Exception) {
            // Handle error
        }
    }
}