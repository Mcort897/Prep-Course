package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.SafeZone
import kotlinx.coroutines.flow.Flow

@Dao
interface SafeZoneDao {
    
    @Query("SELECT * FROM safezone WHERE userId = :userId ORDER BY name ASC")
    fun getSafeZonesByUserId(userId: String): Flow<List<SafeZone>>
    
    @Query("SELECT * FROM safezone WHERE userId = :userId AND isActive = 1 ORDER BY name ASC")
    fun getActiveSafeZonesByUserId(userId: String): Flow<List<SafeZone>>
    
    @Query("SELECT * FROM safezone WHERE id = :zoneId")
    suspend fun getSafeZoneById(zoneId: String): SafeZone?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSafeZone(safeZone: SafeZone)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSafeZones(safeZones: List<SafeZone>)
    
    @Update
    suspend fun updateSafeZone(safeZone: SafeZone)
    
    @Delete
    suspend fun deleteSafeZone(safeZone: SafeZone)
    
    @Query("DELETE FROM safezone WHERE id = :zoneId")
    suspend fun deleteSafeZoneById(zoneId: String)
    
    @Query("DELETE FROM safezone WHERE userId = :userId")
    suspend fun deleteSafeZonesByUserId(userId: String)
    
    @Query("UPDATE safezone SET isActive = :isActive WHERE id = :zoneId")
    suspend fun updateSafeZoneActiveStatus(zoneId: String, isActive: Boolean)
}