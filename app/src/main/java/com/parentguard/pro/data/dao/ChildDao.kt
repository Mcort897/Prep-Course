package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.Child
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {
    
    @Query("SELECT * FROM children WHERE id = :childId")
    suspend fun getChildById(childId: String): Child?
    
    @Query("SELECT * FROM children WHERE parentId = :parentId")
    suspend fun getChildrenByParentId(parentId: String): List<Child>
    
    @Query("SELECT * FROM children WHERE parentId = :parentId")
    fun getChildrenByParentIdFlow(parentId: String): Flow<List<Child>>
    
    @Query("SELECT * FROM children WHERE parentId = :parentId AND isActive = 1")
    suspend fun getActiveChildrenByParentId(parentId: String): List<Child>
    
    @Query("SELECT * FROM children WHERE parentId = :parentId AND isActive = 1")
    fun getActiveChildrenByParentIdFlow(parentId: String): Flow<List<Child>>
    
    @Query("SELECT * FROM children WHERE isActive = 1")
    suspend fun getActiveChildren(): List<Child>
    
    @Query("SELECT * FROM children WHERE isActive = 1")
    fun getActiveChildrenFlow(): Flow<List<Child>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: Child)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildren(children: List<Child>)
    
    @Update
    suspend fun updateChild(child: Child)
    
    @Delete
    suspend fun deleteChild(child: Child)
    
    @Query("DELETE FROM children WHERE id = :childId")
    suspend fun deleteChildById(childId: String)
    
    @Query("UPDATE children SET isActive = :isActive WHERE id = :childId")
    suspend fun updateChildActiveStatus(childId: String, isActive: Boolean)
    
    @Query("UPDATE children SET lastSeenAt = :lastSeenAt WHERE id = :childId")
    suspend fun updateLastSeen(childId: String, lastSeenAt: java.util.Date)
    
    @Query("UPDATE children SET currentLocation = :location WHERE id = :childId")
    suspend fun updateCurrentLocation(childId: String, location: com.parentguard.pro.data.model.Location)
    
    @Query("UPDATE children SET deviceInfo = :deviceInfo WHERE id = :childId")
    suspend fun updateDeviceInfo(childId: String, deviceInfo: com.parentguard.pro.data.model.DeviceInfo)
    
    @Query("UPDATE children SET restrictions = :restrictions WHERE id = :childId")
    suspend fun updateRestrictions(childId: String, restrictions: com.parentguard.pro.data.model.ChildRestrictions)
    
    @Query("SELECT COUNT(*) FROM children WHERE parentId = :parentId")
    suspend fun getChildCountByParentId(parentId: String): Int
    
    @Query("SELECT COUNT(*) FROM children WHERE parentId = :parentId AND isActive = 1")
    suspend fun getActiveChildCountByParentId(parentId: String): Int
}