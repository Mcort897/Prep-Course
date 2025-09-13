package com.safeguard.parental.data.local.dao

import androidx.room.*
import com.safeguard.parental.data.local.entity.ChildEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChildDao {
    
    @Query("SELECT * FROM children WHERE parentId = :parentId AND isActive = 1")
    fun getChildrenByParent(parentId: String): Flow<List<ChildEntity>>
    
    @Query("SELECT * FROM children WHERE id = :childId")
    suspend fun getChildById(childId: String): ChildEntity?
    
    @Query("SELECT * FROM children WHERE deviceId = :deviceId")
    suspend fun getChildByDeviceId(deviceId: String): ChildEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChild(child: ChildEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChildren(children: List<ChildEntity>)
    
    @Update
    suspend fun updateChild(child: ChildEntity)
    
    @Delete
    suspend fun deleteChild(child: ChildEntity)
    
    @Query("DELETE FROM children WHERE id = :childId")
    suspend fun deleteChildById(childId: String)
    
    @Query("UPDATE children SET status = :status, lastSeen = :lastSeen WHERE id = :childId")
    suspend fun updateChildStatus(childId: String, status: String, lastSeen: java.util.Date)
    
    @Query("UPDATE children SET currentLatitude = :latitude, currentLongitude = :longitude WHERE id = :childId")
    suspend fun updateChildLocation(childId: String, latitude: Double, longitude: Double)
    
    @Query("SELECT COUNT(*) FROM children WHERE parentId = :parentId AND isActive = 1")
    suspend fun getActiveChildrenCount(parentId: String): Int
}