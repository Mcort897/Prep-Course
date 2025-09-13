package com.safeguard.parental.data.local.dao

import androidx.room.*
import com.safeguard.parental.data.local.entity.LocationEntity
import com.safeguard.parental.data.local.entity.SafeZoneEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    
    @Query("SELECT * FROM locations WHERE childId = :childId ORDER BY timestamp DESC")
    fun getLocationsByChild(childId: String): Flow<List<LocationEntity>>
    
    @Query("SELECT * FROM locations WHERE childId = :childId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastLocationByChild(childId: String): LocationEntity?
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND timestamp BETWEEN :startTime AND :endTime ORDER BY timestamp ASC")
    suspend fun getLocationsByTimeRange(childId: String, startTime: java.util.Date, endTime: java.util.Date): List<LocationEntity>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationEntity)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationEntity>)
    
    @Delete
    suspend fun deleteLocation(location: LocationEntity)
    
    @Query("DELETE FROM locations WHERE childId = :childId AND timestamp < :beforeDate")
    suspend fun deleteOldLocations(childId: String, beforeDate: java.util.Date)
    
    // Safe Zones
    @Query("SELECT * FROM safe_zones WHERE parentId = :parentId AND isActive = 1")
    fun getSafeZonesByParent(parentId: String): Flow<List<SafeZoneEntity>>
    
    @Query("SELECT * FROM safe_zones WHERE childId = :childId AND isActive = 1")
    fun getSafeZonesByChild(childId: String): Flow<List<SafeZoneEntity>>
    
    @Query("SELECT * FROM safe_zones WHERE id = :safeZoneId")
    suspend fun getSafeZoneById(safeZoneId: String): SafeZoneEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSafeZone(safeZone: SafeZoneEntity)
    
    @Update
    suspend fun updateSafeZone(safeZone: SafeZoneEntity)
    
    @Delete
    suspend fun deleteSafeZone(safeZone: SafeZoneEntity)
    
    @Query("DELETE FROM safe_zones WHERE id = :safeZoneId")
    suspend fun deleteSafeZoneById(safeZoneId: String)
}