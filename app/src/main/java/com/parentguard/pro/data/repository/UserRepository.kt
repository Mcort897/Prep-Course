package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.UserDao
import com.parentguard.pro.data.model.User
import com.parentguard.pro.data.model.UserType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    
    suspend fun getUserById(userId: String): User? = userDao.getUserById(userId)
    
    suspend fun getUserByEmail(email: String): User? = userDao.getUserByEmail(email)
    
    suspend fun getUsersByFamilyCode(familyCode: String, userType: UserType): List<User> = 
        userDao.getUsersByFamilyCode(familyCode, userType)
    
    suspend fun getUsersByType(userType: UserType): List<User> = 
        userDao.getUsersByType(userType)
    
    suspend fun getActiveUsers(): List<User> = userDao.getActiveUsers()
    
    fun getActiveUsersFlow(): Flow<List<User>> = userDao.getActiveUsersFlow()
    
    suspend fun getActiveUsersByFamilyCode(familyCode: String): List<User> = 
        userDao.getActiveUsersByFamilyCode(familyCode)
    
    fun getActiveUsersByFamilyCodeFlow(familyCode: String): Flow<List<User>> = 
        userDao.getActiveUsersByFamilyCodeFlow(familyCode)
    
    suspend fun insertUser(user: User) = userDao.insertUser(user)
    
    suspend fun insertUsers(users: List<User>) = userDao.insertUsers(users)
    
    suspend fun updateUser(user: User) = userDao.updateUser(user)
    
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    
    suspend fun deleteUserById(userId: String) = userDao.deleteUserById(userId)
    
    suspend fun updateUserActiveStatus(userId: String, isActive: Boolean) = 
        userDao.updateUserActiveStatus(userId, isActive)
    
    suspend fun updateLastLogin(userId: String, lastLoginAt: java.util.Date) = 
        userDao.updateLastLogin(userId, lastLoginAt)
    
    suspend fun updateUserSettings(userId: String, settings: com.parentguard.pro.data.model.UserSettings) = 
        userDao.updateUserSettings(userId, settings)
    
    suspend fun getUserCountByFamilyCode(familyCode: String): Int = 
        userDao.getUserCountByFamilyCode(familyCode)
    
    suspend fun getUserCountByFamilyCodeAndType(familyCode: String, userType: UserType): Int = 
        userDao.getUserCountByFamilyCodeAndType(familyCode, userType)
}