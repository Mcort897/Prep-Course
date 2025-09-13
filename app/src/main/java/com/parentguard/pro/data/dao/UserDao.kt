package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.User
import com.parentguard.pro.data.model.UserType
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    
    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): User?
    
    @Query("SELECT * FROM users WHERE email = :email")
    suspend fun getUserByEmail(email: String): User?
    
    @Query("SELECT * FROM users WHERE familyCode = :familyCode AND userType = :userType")
    suspend fun getUsersByFamilyCode(familyCode: String, userType: UserType): List<User>
    
    @Query("SELECT * FROM users WHERE userType = :userType")
    suspend fun getUsersByType(userType: UserType): List<User>
    
    @Query("SELECT * FROM users WHERE isActive = 1")
    suspend fun getActiveUsers(): List<User>
    
    @Query("SELECT * FROM users WHERE isActive = 1")
    fun getActiveUsersFlow(): Flow<List<User>>
    
    @Query("SELECT * FROM users WHERE familyCode = :familyCode AND isActive = 1")
    suspend fun getActiveUsersByFamilyCode(familyCode: String): List<User>
    
    @Query("SELECT * FROM users WHERE familyCode = :familyCode AND isActive = 1")
    fun getActiveUsersByFamilyCodeFlow(familyCode: String): Flow<List<User>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<User>)
    
    @Update
    suspend fun updateUser(user: User)
    
    @Delete
    suspend fun deleteUser(user: User)
    
    @Query("DELETE FROM users WHERE id = :userId")
    suspend fun deleteUserById(userId: String)
    
    @Query("UPDATE users SET isActive = :isActive WHERE id = :userId")
    suspend fun updateUserActiveStatus(userId: String, isActive: Boolean)
    
    @Query("UPDATE users SET lastLoginAt = :lastLoginAt WHERE id = :userId")
    suspend fun updateLastLogin(userId: String, lastLoginAt: java.util.Date)
    
    @Query("UPDATE users SET settings = :settings WHERE id = :userId")
    suspend fun updateUserSettings(userId: String, settings: com.parentguard.pro.data.model.UserSettings)
    
    @Query("SELECT COUNT(*) FROM users WHERE familyCode = :familyCode")
    suspend fun getUserCountByFamilyCode(familyCode: String): Int
    
    @Query("SELECT COUNT(*) FROM users WHERE familyCode = :familyCode AND userType = :userType")
    suspend fun getUserCountByFamilyCodeAndType(familyCode: String, userType: UserType): Int
}