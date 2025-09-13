package com.parentalcontrolpro.data.dao

import androidx.room.*
import com.parentalcontrolpro.data.model.Report
import kotlinx.coroutines.flow.Flow

@Dao
interface ReportDao {
    
    @Query("SELECT * FROM report WHERE userId = :userId ORDER BY createdAt DESC")
    fun getReportsByUserId(userId: String): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE parentId = :parentId ORDER BY createdAt DESC")
    fun getReportsByParentId(parentId: String): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE userId = :userId AND reportType = :reportType ORDER BY createdAt DESC")
    fun getReportsByUserIdAndType(userId: String, reportType: String): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE parentId = :parentId AND reportType = :reportType ORDER BY createdAt DESC")
    fun getReportsByParentIdAndType(parentId: String, reportType: String): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE userId = :userId AND startDate BETWEEN :startDate AND :endDate ORDER BY createdAt DESC")
    fun getReportsByUserIdAndDateRange(userId: String, startDate: Long, endDate: Long): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE parentId = :parentId AND startDate BETWEEN :startDate AND :endDate ORDER BY createdAt DESC")
    fun getReportsByParentIdAndDateRange(parentId: String, startDate: Long, endDate: Long): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE id = :reportId")
    suspend fun getReportById(reportId: String): Report?
    
    @Query("SELECT * FROM report WHERE userId = :userId AND isGenerated = 0 ORDER BY createdAt DESC")
    fun getPendingReportsByUserId(userId: String): Flow<List<Report>>
    
    @Query("SELECT * FROM report WHERE parentId = :parentId AND isGenerated = 0 ORDER BY createdAt DESC")
    fun getPendingReportsByParentId(parentId: String): Flow<List<Report>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: Report)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReports(reports: List<Report>)
    
    @Update
    suspend fun updateReport(report: Report)
    
    @Delete
    suspend fun deleteReport(report: Report)
    
    @Query("DELETE FROM report WHERE userId = :userId")
    suspend fun deleteReportsByUserId(userId: String)
    
    @Query("UPDATE report SET isGenerated = :isGenerated WHERE id = :id")
    suspend fun updateReportGeneratedStatus(id: String, isGenerated: Boolean)
    
    @Query("DELETE FROM report WHERE createdAt < :date")
    suspend fun deleteOldReports(date: Long)
}