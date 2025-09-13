package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.DangerZone
import kotlinx.coroutines.flow.Flow

@Dao
interface DangerZoneDao {
    
    @Query("SELECT * FROM dangerzone WHERE userId = :userId ORDER BY name ASC")
    fun getDangerZonesByUserId(userId: String): Flow<List<DangerZone>>
    
    @Query("SELECT * FROM dangerzone WHERE userId = :userId AND isActive = 1 ORDER BY name ASC")
    fun getActiveDangerZonesByUserId(userId: String): Flow<List<DangerZone>>
    
    @Query("SELECT * FROM dangerzone WHERE id = :zoneId")
    suspend fun getDangerZoneById(zoneId: String): DangerZone?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDangerZone(dangerZone: DangerZone)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDangerZones(dangerZones: List<DangerZone>)
    
    @Update
    suspend fun updateDangerZone(dangerZone: DangerZone)
    
    @Delete
    suspend fun deleteDangerZone(dangerZone: DangerZone)
    
    @Query("DELETE FROM dangerzone WHERE id = :zoneId")
    suspend fun deleteDangerZoneById(zoneId: String)
    
    @Query("DELETE FROM dangerzone WHERE userId = :userId")
    suspend fun deleteDangerZonesByUserId(userId: String)
    
    @Query("UPDATE dangerzone SET isActive = :isActive WHERE id = :zoneId")
    suspend fun updateDangerZoneActiveStatus(zoneId: String, isActive: Boolean)
}