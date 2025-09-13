package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.LocationData
import kotlinx.coroutines.flow.Flow

@Dao
interface LocationDao {
    
    @Query("SELECT * FROM locationdata WHERE userId = :userId ORDER BY timestamp DESC")
    fun getLocationDataByUserId(userId: String): Flow<List<LocationData>>
    
    @Query("SELECT * FROM locationdata WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getLocationDataByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<LocationData>>
    
    @Query("SELECT * FROM locationdata WHERE userId = :userId AND isInSafeZone = 1 ORDER BY timestamp DESC")
    fun getSafeZoneLocationDataByUserId(userId: String): Flow<List<LocationData>>
    
    @Query("SELECT * FROM locationdata WHERE userId = :userId AND isInDangerZone = 1 ORDER BY timestamp DESC")
    fun getDangerZoneLocationDataByUserId(userId: String): Flow<List<LocationData>>
    
    @Query("SELECT * FROM locationdata WHERE userId = :userId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestLocationByUserId(userId: String): LocationData?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationData(locationData: LocationData)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationDataList(locationDataList: List<LocationData>)
    
    @Update
    suspend fun updateLocationData(locationData: LocationData)
    
    @Delete
    suspend fun deleteLocationData(locationData: LocationData)
    
    @Query("DELETE FROM locationdata WHERE userId = :userId")
    suspend fun deleteLocationDataByUserId(userId: String)
    
    @Query("DELETE FROM locationdata WHERE userId = :userId AND timestamp < :date")
    suspend fun deleteOldLocationDataByUserId(userId: String, date: Long)
}