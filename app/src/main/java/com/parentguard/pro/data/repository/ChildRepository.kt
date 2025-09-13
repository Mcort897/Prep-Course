package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.ChildDao
import com.parentguard.pro.data.model.Child
import com.parentguard.pro.data.model.ChildRestrictions
import com.parentguard.pro.data.model.DeviceInfo
import com.parentguard.pro.data.model.Location
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChildRepository @Inject constructor(
    private val childDao: ChildDao
) {
    
    suspend fun getChildById(childId: String): Child? = childDao.getChildById(childId)
    
    suspend fun getChildrenByParentId(parentId: String): List<Child> = 
        childDao.getChildrenByParentId(parentId)
    
    fun getChildrenByParentIdFlow(parentId: String): Flow<List<Child>> = 
        childDao.getChildrenByParentIdFlow(parentId)
    
    suspend fun getActiveChildrenByParentId(parentId: String): List<Child> = 
        childDao.getActiveChildrenByParentId(parentId)
    
    fun getActiveChildrenByParentIdFlow(parentId: String): Flow<List<Child>> = 
        childDao.getActiveChildrenByParentIdFlow(parentId)
    
    suspend fun getActiveChildren(): List<Child> = childDao.getActiveChildren()
    
    fun getActiveChildrenFlow(): Flow<List<Child>> = childDao.getActiveChildrenFlow()
    
    suspend fun insertChild(child: Child) = childDao.insertChild(child)
    
    suspend fun insertChildren(children: List<Child>) = childDao.insertChildren(children)
    
    suspend fun updateChild(child: Child) = childDao.updateChild(child)
    
    suspend fun deleteChild(child: Child) = childDao.deleteChild(child)
    
    suspend fun deleteChildById(childId: String) = childDao.deleteChildById(childId)
    
    suspend fun updateChildActiveStatus(childId: String, isActive: Boolean) = 
        childDao.updateChildActiveStatus(childId, isActive)
    
    suspend fun updateLastSeen(childId: String, lastSeenAt: java.util.Date) = 
        childDao.updateLastSeen(childId, lastSeenAt)
    
    suspend fun updateCurrentLocation(childId: String, location: Location) = 
        childDao.updateCurrentLocation(childId, location)
    
    suspend fun updateDeviceInfo(childId: String, deviceInfo: DeviceInfo) = 
        childDao.updateDeviceInfo(childId, deviceInfo)
    
    suspend fun updateRestrictions(childId: String, restrictions: ChildRestrictions) = 
        childDao.updateRestrictions(childId, restrictions)
    
    suspend fun getChildCountByParentId(parentId: String): Int = 
        childDao.getChildCountByParentId(parentId)
    
    suspend fun getActiveChildCountByParentId(parentId: String): Int = 
        childDao.getActiveChildCountByParentId(parentId)
}