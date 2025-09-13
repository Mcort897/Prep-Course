package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.LocationDao
import com.parentguard.pro.data.model.Location
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocationRepository @Inject constructor(
    private val locationDao: LocationDao
) {
    
    suspend fun getLocationsByChildId(childId: String): List<Location> = 
        locationDao.getLocationsByChildId(childId)
    
    fun getLocationsByChildIdFlow(childId: String): Flow<List<Location>> = 
        locationDao.getLocationsByChildIdFlow(childId)
    
    suspend fun getLocationsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<Location> = 
        locationDao.getLocationsByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun getLatestLocationByChildId(childId: String): Location? = 
        locationDao.getLatestLocationByChildId(childId)
    
    fun getLatestLocationByChildIdFlow(childId: String): Flow<Location?> = 
        locationDao.getLatestLocationByChildIdFlow(childId)
    
    suspend fun getLocationsByChildIdSince(childId: String, since: Date): List<Location> = 
        locationDao.getLocationsByChildIdSince(childId, since)
    
    fun getLocationsByChildIdSinceFlow(childId: String, since: Date): Flow<List<Location>> = 
        locationDao.getLocationsByChildIdSinceFlow(childId, since)
    
    suspend fun getLocationsInArea(childId: String, minLat: Double, maxLat: Double, minLng: Double, maxLng: Double): List<Location> = 
        locationDao.getLocationsInArea(childId, minLat, maxLat, minLng, maxLng)
    
    suspend fun getLocationsByAddressPattern(childId: String, addressPattern: String): List<Location> = 
        locationDao.getLocationsByAddressPattern(childId, addressPattern)
    
    suspend fun insertLocation(location: Location) = locationDao.insertLocation(location)
    
    suspend fun insertLocations(locations: List<Location>) = locationDao.insertLocations(locations)
    
    suspend fun updateLocation(location: Location) = locationDao.updateLocation(location)
    
    suspend fun deleteLocation(location: Location) = locationDao.deleteLocation(location)
    
    suspend fun deleteLocationById(id: String) = locationDao.deleteLocationById(id)
    
    suspend fun deleteLocationsByChildId(childId: String) = locationDao.deleteLocationsByChildId(childId)
    
    suspend fun deleteOldLocations(childId: String, beforeDate: Date) = 
        locationDao.deleteOldLocations(childId, beforeDate)
    
    suspend fun getLocationCountByChildId(childId: String): Int = 
        locationDao.getLocationCountByChildId(childId)
    
    suspend fun getLocationCountByChildIdSince(childId: String, since: Date): Int = 
        locationDao.getLocationCountByChildIdSince(childId, since)
    
    suspend fun getLocationsForRoute(childId: String, startDate: Date, endDate: Date): List<Location> = 
        locationDao.getLocationsForRoute(childId, startDate, endDate)
    
    suspend fun getDistinctAddressesByChildId(childId: String): List<String> = 
        locationDao.getDistinctAddressesByChildId(childId)
    
    suspend fun getLocationsByAddress(childId: String, address: String): List<Location> = 
        locationDao.getLocationsByAddress(childId, address)
    
    suspend fun getLocationsByCoordinates(childId: String, latitude: Double, longitude: Double): List<Location> = 
        locationDao.getLocationsByCoordinates(childId, latitude, longitude)
    
    suspend fun getDailyLocationSummary(childId: String, startDate: Date, endDate: Date): List<Location> = 
        locationDao.getDailyLocationSummary(childId, startDate, endDate)
}