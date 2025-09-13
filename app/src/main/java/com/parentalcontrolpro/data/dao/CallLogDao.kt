package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.CallLog
import kotlinx.coroutines.flow.Flow

@Dao
interface CallLogDao {
    
    @Query("SELECT * FROM calllog WHERE userId = :userId ORDER BY timestamp DESC")
    fun getCallLogsByUserId(userId: String): Flow<List<CallLog>>
    
    @Query("SELECT * FROM calllog WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate ORDER BY timestamp DESC")
    fun getCallLogsByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<CallLog>>
    
    @Query("SELECT * FROM calllog WHERE userId = :userId AND isBlocked = 1 ORDER BY timestamp DESC")
    fun getBlockedCallLogsByUserId(userId: String): Flow<List<CallLog>>
    
    @Query("SELECT phoneNumber, COUNT(*) as callCount FROM calllog WHERE userId = :userId AND timestamp BETWEEN :startDate AND :endDate GROUP BY phoneNumber ORDER BY callCount DESC")
    suspend fun getMostCalledNumbersByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): List<CallLogSummary>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCallLog(callLog: CallLog)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCallLogs(callLogs: List<CallLog>)
    
    @Update
    suspend fun updateCallLog(callLog: CallLog)
    
    @Delete
    suspend fun deleteCallLog(callLog: CallLog)
    
    @Query("DELETE FROM calllog WHERE userId = :userId")
    suspend fun deleteCallLogsByUserId(userId: String)
    
    data class CallLogSummary(
        val phoneNumber: String,
        val callCount: Int
    )
}