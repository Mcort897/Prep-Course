package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.Location
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface LocationDao {
    
    @Query("SELECT * FROM locations WHERE childId = :childId ORDER BY timestamp DESC")
    suspend fun getLocationsByChildId(childId: String): List<Location>
    
    @Query("SELECT * FROM locations WHERE childId = :childId ORDER BY timestamp DESC")
    fun getLocationsByChildIdFlow(childId: String): Flow<List<Location>>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    suspend fun getLocationsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<Location>
    
    @Query("SELECT * FROM locations WHERE childId = :childId ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestLocationByChildId(childId: String): Location?
    
    @Query("SELECT * FROM locations WHERE childId = :childId ORDER BY timestamp DESC LIMIT 1")
    fun getLatestLocationByChildIdFlow(childId: String): Flow<Location?>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND timestamp >= :since ORDER BY timestamp DESC")
    suspend fun getLocationsByChildIdSince(childId: String, since: Date): List<Location>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND timestamp >= :since ORDER BY timestamp DESC")
    fun getLocationsByChildIdSinceFlow(childId: String, since: Date): Flow<List<Location>>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND latitude BETWEEN :minLat AND :maxLat AND longitude BETWEEN :minLng AND :maxLng ORDER BY timestamp DESC")
    suspend fun getLocationsInArea(childId: String, minLat: Double, maxLat: Double, minLng: Double, maxLng: Double): List<Location>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND address LIKE :addressPattern ORDER BY timestamp DESC")
    suspend fun getLocationsByAddressPattern(childId: String, addressPattern: String): List<Location>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)
    
    @Update
    suspend fun updateLocation(location: Location)
    
    @Delete
    suspend fun deleteLocation(location: Location)
    
    @Query("DELETE FROM locations WHERE id = :id")
    suspend fun deleteLocationById(id: String)
    
    @Query("DELETE FROM locations WHERE childId = :childId")
    suspend fun deleteLocationsByChildId(childId: String)
    
    @Query("DELETE FROM locations WHERE childId = :childId AND timestamp < :beforeDate")
    suspend fun deleteOldLocations(childId: String, beforeDate: Date)
    
    @Query("SELECT COUNT(*) FROM locations WHERE childId = :childId")
    suspend fun getLocationCountByChildId(childId: String): Int
    
    @Query("SELECT COUNT(*) FROM locations WHERE childId = :childId AND timestamp >= :since")
    suspend fun getLocationCountByChildIdSince(childId: String, since: Date): Int
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND timestamp >= :startDate AND timestamp <= :endDate ORDER BY timestamp ASC")
    suspend fun getLocationsForRoute(childId: String, startDate: Date, endDate: Date): List<Location>
    
    @Query("SELECT DISTINCT address FROM locations WHERE childId = :childId AND address IS NOT NULL ORDER BY address")
    suspend fun getDistinctAddressesByChildId(childId: String): List<String>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND address = :address ORDER BY timestamp DESC")
    suspend fun getLocationsByAddress(childId: String, address: String): List<Location>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND latitude = :latitude AND longitude = :longitude ORDER BY timestamp DESC")
    suspend fun getLocationsByCoordinates(childId: String, latitude: Double, longitude: Double): List<Location>
    
    @Query("SELECT * FROM locations WHERE childId = :childId AND timestamp >= :startDate AND timestamp <= :endDate GROUP BY DATE(timestamp/1000, 'unixepoch') ORDER BY timestamp DESC")
    suspend fun getDailyLocationSummary(childId: String, startDate: Date, endDate: Date): List<Location>
}