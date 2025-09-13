package com.parentguard.pro.data.dao

import androidx.room.*
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface WebFilterDao {
    
    // Web Filters
    @Query("SELECT * FROM web_filters WHERE childId = :childId ORDER BY createdAt DESC")
    suspend fun getWebFiltersByChildId(childId: String): List<WebFilter>
    
    @Query("SELECT * FROM web_filters WHERE childId = :childId ORDER BY createdAt DESC")
    fun getWebFiltersByChildIdFlow(childId: String): Flow<List<WebFilter>>
    
    @Query("SELECT * FROM web_filters WHERE childId = :childId AND isActive = 1 ORDER BY createdAt DESC")
    suspend fun getActiveWebFiltersByChildId(childId: String): List<WebFilter>
    
    @Query("SELECT * FROM web_filters WHERE parentId = :parentId ORDER BY createdAt DESC")
    suspend fun getWebFiltersByParentId(parentId: String): List<WebFilter>
    
    @Query("SELECT * FROM web_filters WHERE id = :webFilterId")
    suspend fun getWebFilterById(webFilterId: String): WebFilter?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilter(webFilter: WebFilter)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilters(webFilters: List<WebFilter>)
    
    @Update
    suspend fun updateWebFilter(webFilter: WebFilter)
    
    @Delete
    suspend fun deleteWebFilter(webFilter: WebFilter)
    
    @Query("DELETE FROM web_filters WHERE id = :webFilterId")
    suspend fun deleteWebFilterById(webFilterId: String)
    
    @Query("UPDATE web_filters SET isActive = :isActive WHERE id = :webFilterId")
    suspend fun updateWebFilterActiveStatus(webFilterId: String, isActive: Boolean)
    
    @Query("UPDATE web_filters SET lastModifiedAt = :lastModifiedAt WHERE id = :webFilterId")
    suspend fun updateWebFilterLastModified(webFilterId: String, lastModifiedAt: Date)
    
    // Web Filter Rules
    @Query("SELECT * FROM web_filter_rules WHERE webFilterId = :webFilterId ORDER BY createdAt DESC")
    suspend fun getWebFilterRulesByWebFilterId(webFilterId: String): List<WebFilterRule>
    
    @Query("SELECT * FROM web_filter_rules WHERE webFilterId = :webFilterId ORDER BY createdAt DESC")
    fun getWebFilterRulesByWebFilterIdFlow(webFilterId: String): Flow<List<WebFilterRule>>
    
    @Query("SELECT * FROM web_filter_rules WHERE webFilterId = :webFilterId AND isActive = 1 ORDER BY createdAt DESC")
    suspend fun getActiveWebFilterRulesByWebFilterId(webFilterId: String): List<WebFilterRule>
    
    @Query("SELECT * FROM web_filter_rules WHERE id = :ruleId")
    suspend fun getWebFilterRuleById(ruleId: String): WebFilterRule?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterRule(rule: WebFilterRule)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterRules(rules: List<WebFilterRule>)
    
    @Update
    suspend fun updateWebFilterRule(rule: WebFilterRule)
    
    @Delete
    suspend fun deleteWebFilterRule(rule: WebFilterRule)
    
    @Query("DELETE FROM web_filter_rules WHERE id = :ruleId")
    suspend fun deleteWebFilterRuleById(ruleId: String)
    
    @Query("UPDATE web_filter_rules SET isActive = :isActive WHERE id = :ruleId")
    suspend fun updateWebFilterRuleActiveStatus(ruleId: String, isActive: Boolean)
    
    // Web Filter Categories
    @Query("SELECT * FROM web_filter_categories WHERE isActive = 1 ORDER BY name ASC")
    suspend fun getActiveWebFilterCategories(): List<WebFilterCategory>
    
    @Query("SELECT * FROM web_filter_categories WHERE isActive = 1 ORDER BY name ASC")
    fun getActiveWebFilterCategoriesFlow(): Flow<List<WebFilterCategory>>
    
    @Query("SELECT * FROM web_filter_categories WHERE id = :categoryId")
    suspend fun getWebFilterCategoryById(categoryId: String): WebFilterCategory?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterCategory(category: WebFilterCategory)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterCategories(categories: List<WebFilterCategory>)
    
    @Update
    suspend fun updateWebFilterCategory(category: WebFilterCategory)
    
    @Delete
    suspend fun deleteWebFilterCategory(category: WebFilterCategory)
    
    @Query("DELETE FROM web_filter_categories WHERE id = :categoryId")
    suspend fun deleteWebFilterCategoryById(categoryId: String)
    
    @Query("UPDATE web_filter_categories SET isActive = :isActive WHERE id = :categoryId")
    suspend fun updateWebFilterCategoryActiveStatus(categoryId: String, isActive: Boolean)
    
    // Web Filter Violations
    @Query("SELECT * FROM web_filter_violations WHERE childId = :childId ORDER BY violatedAt DESC")
    suspend fun getWebFilterViolationsByChildId(childId: String): List<WebFilterViolation>
    
    @Query("SELECT * FROM web_filter_violations WHERE childId = :childId ORDER BY violatedAt DESC")
    fun getWebFilterViolationsByChildIdFlow(childId: String): Flow<List<WebFilterViolation>>
    
    @Query("SELECT * FROM web_filter_violations WHERE childId = :childId AND isResolved = 0 ORDER BY violatedAt DESC")
    suspend fun getUnresolvedWebFilterViolationsByChildId(childId: String): List<WebFilterViolation>
    
    @Query("SELECT * FROM web_filter_violations WHERE childId = :childId AND violationType = :violationType ORDER BY violatedAt DESC")
    suspend fun getWebFilterViolationsByChildIdAndType(childId: String, violationType: WebFilterViolationType): List<WebFilterViolation>
    
    @Query("SELECT * FROM web_filter_violations WHERE childId = :childId AND violatedAt BETWEEN :startDate AND :endDate ORDER BY violatedAt DESC")
    suspend fun getWebFilterViolationsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<WebFilterViolation>
    
    @Query("SELECT * FROM web_filter_violations WHERE childId = :childId AND severity = :severity ORDER BY violatedAt DESC")
    suspend fun getWebFilterViolationsByChildIdAndSeverity(childId: String, severity: ViolationSeverity): List<WebFilterViolation>
    
    @Query("SELECT * FROM web_filter_violations WHERE webFilterId = :webFilterId ORDER BY violatedAt DESC")
    suspend fun getWebFilterViolationsByWebFilterId(webFilterId: String): List<WebFilterViolation>
    
    @Query("SELECT * FROM web_filter_violations WHERE webFilterRuleId = :ruleId ORDER BY violatedAt DESC")
    suspend fun getWebFilterViolationsByRuleId(ruleId: String): List<WebFilterViolation>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterViolation(violation: WebFilterViolation)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterViolations(violations: List<WebFilterViolation>)
    
    @Update
    suspend fun updateWebFilterViolation(violation: WebFilterViolation)
    
    @Delete
    suspend fun deleteWebFilterViolation(violation: WebFilterViolation)
    
    @Query("DELETE FROM web_filter_violations WHERE id = :id")
    suspend fun deleteWebFilterViolationById(id: String)
    
    @Query("UPDATE web_filter_violations SET isResolved = :isResolved, resolvedAt = :resolvedAt, resolutionNotes = :resolutionNotes WHERE id = :id")
    suspend fun resolveWebFilterViolation(id: String, isResolved: Boolean, resolvedAt: Date?, resolutionNotes: String?)
    
    @Query("SELECT COUNT(*) FROM web_filter_violations WHERE childId = :childId AND isResolved = 0")
    suspend fun getUnresolvedViolationCountByChildId(childId: String): Int
    
    @Query("SELECT COUNT(*) FROM web_filter_violations WHERE childId = :childId AND violationType = :violationType")
    suspend fun getViolationCountByChildIdAndType(childId: String, violationType: WebFilterViolationType): Int
    
    @Query("SELECT COUNT(*) FROM web_filter_violations WHERE childId = :childId AND severity = :severity")
    suspend fun getViolationCountByChildIdAndSeverity(childId: String, severity: ViolationSeverity): Int
    
    // Web Filter Stats
    @Query("SELECT * FROM web_filter_stats WHERE childId = :childId ORDER BY date DESC")
    suspend fun getWebFilterStatsByChildId(childId: String): List<WebFilterStats>
    
    @Query("SELECT * FROM web_filter_stats WHERE childId = :childId ORDER BY date DESC")
    fun getWebFilterStatsByChildIdFlow(childId: String): Flow<List<WebFilterStats>>
    
    @Query("SELECT * FROM web_filter_stats WHERE childId = :childId AND date = :date")
    suspend fun getWebFilterStatsByChildIdAndDate(childId: String, date: Date): WebFilterStats?
    
    @Query("SELECT * FROM web_filter_stats WHERE childId = :childId AND date BETWEEN :startDate AND :endDate ORDER BY date DESC")
    suspend fun getWebFilterStatsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<WebFilterStats>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterStats(stats: WebFilterStats)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWebFilterStatsList(statsList: List<WebFilterStats>)
    
    @Update
    suspend fun updateWebFilterStats(stats: WebFilterStats)
    
    @Delete
    suspend fun deleteWebFilterStats(stats: WebFilterStats)
    
    @Query("DELETE FROM web_filter_stats WHERE id = :id")
    suspend fun deleteWebFilterStatsById(id: String)
    
    @Query("UPDATE web_filter_stats SET totalRequests = :totalRequests, blockedRequests = :blockedRequests, allowedRequests = :allowedRequests WHERE id = :id")
    suspend fun updateWebFilterStatsCounts(id: String, totalRequests: Int, blockedRequests: Int, allowedRequests: Int)
}