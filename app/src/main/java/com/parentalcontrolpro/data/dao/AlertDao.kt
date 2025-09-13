package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.Alert
import kotlinx.coroutines.flow.Flow

@Dao
interface AlertDao {
    
    @Query("SELECT * FROM alert WHERE userId = :userId ORDER BY timestamp DESC")
    fun getAlertsByUserId(userId: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE parentId = :parentId ORDER BY timestamp DESC")
    fun getAlertsByParentId(parentId: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE userId = :userId AND isRead = 0 ORDER BY timestamp DESC")
    fun getUnreadAlertsByUserId(userId: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE parentId = :parentId AND isRead = 0 ORDER BY timestamp DESC")
    fun getUnreadAlertsByParentId(parentId: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE userId = :userId AND isResolved = 0 ORDER BY timestamp DESC")
    fun getUnresolvedAlertsByUserId(userId: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE parentId = :parentId AND isResolved = 0 ORDER BY timestamp DESC")
    fun getUnresolvedAlertsByParentId(parentId: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE userId = :userId AND type = :type ORDER BY timestamp DESC")
    fun getAlertsByUserIdAndType(userId: String, type: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE parentId = :parentId AND type = :type ORDER BY timestamp DESC")
    fun getAlertsByParentIdAndType(parentId: String, type: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE userId = :userId AND priority = :priority ORDER BY timestamp DESC")
    fun getAlertsByUserIdAndPriority(userId: String, priority: String): Flow<List<Alert>>
    
    @Query("SELECT * FROM alert WHERE parentId = :parentId AND priority = :priority ORDER BY timestamp DESC")
    fun getAlertsByParentIdAndPriority(parentId: String, priority: String): Flow<List<Alert>>
    
    @Query("SELECT COUNT(*) FROM alert WHERE userId = :userId AND isRead = 0")
    suspend fun getUnreadAlertCountByUserId(userId: String): Int
    
    @Query("SELECT COUNT(*) FROM alert WHERE parentId = :parentId AND isRead = 0")
    suspend fun getUnreadAlertCountByParentId(parentId: String): Int
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlert(alert: Alert)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlerts(alerts: List<Alert>)
    
    @Update
    suspend fun updateAlert(alert: Alert)
    
    @Delete
    suspend fun deleteAlert(alert: Alert)
    
    @Query("DELETE FROM alert WHERE userId = :userId")
    suspend fun deleteAlertsByUserId(userId: String)
    
    @Query("UPDATE alert SET isRead = :isRead WHERE id = :id")
    suspend fun updateAlertReadStatus(id: String, isRead: Boolean)
    
    @Query("UPDATE alert SET isResolved = :isResolved WHERE id = :id")
    suspend fun updateAlertResolvedStatus(id: String, isResolved: Boolean)
    
    @Query("UPDATE alert SET isRead = 1 WHERE userId = :userId")
    suspend fun markAllAlertsAsReadByUserId(userId: String)
    
    @Query("UPDATE alert SET isRead = 1 WHERE parentId = :parentId")
    suspend fun markAllAlertsAsReadByParentId(parentId: String)
}