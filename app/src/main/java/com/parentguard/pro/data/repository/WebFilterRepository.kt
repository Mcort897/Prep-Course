package com.parentguard.pro.data.repository

import com.parentguard.pro.data.dao.WebFilterDao
import com.parentguard.pro.data.model.*
import kotlinx.coroutines.flow.Flow
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WebFilterRepository @Inject constructor(
    private val webFilterDao: WebFilterDao
) {
    
    // Web Filters
    suspend fun getWebFiltersByChildId(childId: String): List<WebFilter> = 
        webFilterDao.getWebFiltersByChildId(childId)
    
    fun getWebFiltersByChildIdFlow(childId: String): Flow<List<WebFilter>> = 
        webFilterDao.getWebFiltersByChildIdFlow(childId)
    
    suspend fun getActiveWebFiltersByChildId(childId: String): List<WebFilter> = 
        webFilterDao.getActiveWebFiltersByChildId(childId)
    
    suspend fun getWebFiltersByParentId(parentId: String): List<WebFilter> = 
        webFilterDao.getWebFiltersByParentId(parentId)
    
    suspend fun getWebFilterById(webFilterId: String): WebFilter? = 
        webFilterDao.getWebFilterById(webFilterId)
    
    suspend fun insertWebFilter(webFilter: WebFilter) = webFilterDao.insertWebFilter(webFilter)
    
    suspend fun insertWebFilters(webFilters: List<WebFilter>) = webFilterDao.insertWebFilters(webFilters)
    
    suspend fun updateWebFilter(webFilter: WebFilter) = webFilterDao.updateWebFilter(webFilter)
    
    suspend fun deleteWebFilter(webFilter: WebFilter) = webFilterDao.deleteWebFilter(webFilter)
    
    suspend fun deleteWebFilterById(webFilterId: String) = webFilterDao.deleteWebFilterById(webFilterId)
    
    suspend fun updateWebFilterActiveStatus(webFilterId: String, isActive: Boolean) = 
        webFilterDao.updateWebFilterActiveStatus(webFilterId, isActive)
    
    suspend fun updateWebFilterLastModified(webFilterId: String, lastModifiedAt: Date) = 
        webFilterDao.updateWebFilterLastModified(webFilterId, lastModifiedAt)
    
    // Web Filter Rules
    suspend fun getWebFilterRulesByWebFilterId(webFilterId: String): List<WebFilterRule> = 
        webFilterDao.getWebFilterRulesByWebFilterId(webFilterId)
    
    fun getWebFilterRulesByWebFilterIdFlow(webFilterId: String): Flow<List<WebFilterRule>> = 
        webFilterDao.getWebFilterRulesByWebFilterIdFlow(webFilterId)
    
    suspend fun getActiveWebFilterRulesByWebFilterId(webFilterId: String): List<WebFilterRule> = 
        webFilterDao.getActiveWebFilterRulesByWebFilterId(webFilterId)
    
    suspend fun getWebFilterRuleById(ruleId: String): WebFilterRule? = 
        webFilterDao.getWebFilterRuleById(ruleId)
    
    suspend fun insertWebFilterRule(rule: WebFilterRule) = webFilterDao.insertWebFilterRule(rule)
    
    suspend fun insertWebFilterRules(rules: List<WebFilterRule>) = webFilterDao.insertWebFilterRules(rules)
    
    suspend fun updateWebFilterRule(rule: WebFilterRule) = webFilterDao.updateWebFilterRule(rule)
    
    suspend fun deleteWebFilterRule(rule: WebFilterRule) = webFilterDao.deleteWebFilterRule(rule)
    
    suspend fun deleteWebFilterRuleById(ruleId: String) = webFilterDao.deleteWebFilterRuleById(ruleId)
    
    suspend fun updateWebFilterRuleActiveStatus(ruleId: String, isActive: Boolean) = 
        webFilterDao.updateWebFilterRuleActiveStatus(ruleId, isActive)
    
    // Web Filter Categories
    suspend fun getActiveWebFilterCategories(): List<WebFilterCategory> = 
        webFilterDao.getActiveWebFilterCategories()
    
    fun getActiveWebFilterCategoriesFlow(): Flow<List<WebFilterCategory>> = 
        webFilterDao.getActiveWebFilterCategoriesFlow()
    
    suspend fun getWebFilterCategoryById(categoryId: String): WebFilterCategory? = 
        webFilterDao.getWebFilterCategoryById(categoryId)
    
    suspend fun insertWebFilterCategory(category: WebFilterCategory) = webFilterDao.insertWebFilterCategory(category)
    
    suspend fun insertWebFilterCategories(categories: List<WebFilterCategory>) = webFilterDao.insertWebFilterCategories(categories)
    
    suspend fun updateWebFilterCategory(category: WebFilterCategory) = webFilterDao.updateWebFilterCategory(category)
    
    suspend fun deleteWebFilterCategory(category: WebFilterCategory) = webFilterDao.deleteWebFilterCategory(category)
    
    suspend fun deleteWebFilterCategoryById(categoryId: String) = webFilterDao.deleteWebFilterCategoryById(categoryId)
    
    suspend fun updateWebFilterCategoryActiveStatus(categoryId: String, isActive: Boolean) = 
        webFilterDao.updateWebFilterCategoryActiveStatus(categoryId, isActive)
    
    // Web Filter Violations
    suspend fun getWebFilterViolationsByChildId(childId: String): List<WebFilterViolation> = 
        webFilterDao.getWebFilterViolationsByChildId(childId)
    
    fun getWebFilterViolationsByChildIdFlow(childId: String): Flow<List<WebFilterViolation>> = 
        webFilterDao.getWebFilterViolationsByChildIdFlow(childId)
    
    suspend fun getUnresolvedWebFilterViolationsByChildId(childId: String): List<WebFilterViolation> = 
        webFilterDao.getUnresolvedWebFilterViolationsByChildId(childId)
    
    suspend fun getWebFilterViolationsByChildIdAndType(childId: String, violationType: WebFilterViolationType): List<WebFilterViolation> = 
        webFilterDao.getWebFilterViolationsByChildIdAndType(childId, violationType)
    
    suspend fun getWebFilterViolationsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<WebFilterViolation> = 
        webFilterDao.getWebFilterViolationsByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun getWebFilterViolationsByChildIdAndSeverity(childId: String, severity: ViolationSeverity): List<WebFilterViolation> = 
        webFilterDao.getWebFilterViolationsByChildIdAndSeverity(childId, severity)
    
    suspend fun getWebFilterViolationsByWebFilterId(webFilterId: String): List<WebFilterViolation> = 
        webFilterDao.getWebFilterViolationsByWebFilterId(webFilterId)
    
    suspend fun getWebFilterViolationsByRuleId(ruleId: String): List<WebFilterViolation> = 
        webFilterDao.getWebFilterViolationsByRuleId(ruleId)
    
    suspend fun insertWebFilterViolation(violation: WebFilterViolation) = webFilterDao.insertWebFilterViolation(violation)
    
    suspend fun insertWebFilterViolations(violations: List<WebFilterViolation>) = webFilterDao.insertWebFilterViolations(violations)
    
    suspend fun updateWebFilterViolation(violation: WebFilterViolation) = webFilterDao.updateWebFilterViolation(violation)
    
    suspend fun deleteWebFilterViolation(violation: WebFilterViolation) = webFilterDao.deleteWebFilterViolation(violation)
    
    suspend fun deleteWebFilterViolationById(id: String) = webFilterDao.deleteWebFilterViolationById(id)
    
    suspend fun resolveWebFilterViolation(id: String, isResolved: Boolean, resolvedAt: Date?, resolutionNotes: String?) = 
        webFilterDao.resolveWebFilterViolation(id, isResolved, resolvedAt, resolutionNotes)
    
    suspend fun getUnresolvedViolationCountByChildId(childId: String): Int = 
        webFilterDao.getUnresolvedViolationCountByChildId(childId)
    
    suspend fun getViolationCountByChildIdAndType(childId: String, violationType: WebFilterViolationType): Int = 
        webFilterDao.getViolationCountByChildIdAndType(childId, violationType)
    
    suspend fun getViolationCountByChildIdAndSeverity(childId: String, severity: ViolationSeverity): Int = 
        webFilterDao.getViolationCountByChildIdAndSeverity(childId, severity)
    
    // Web Filter Stats
    suspend fun getWebFilterStatsByChildId(childId: String): List<WebFilterStats> = 
        webFilterDao.getWebFilterStatsByChildId(childId)
    
    fun getWebFilterStatsByChildIdFlow(childId: String): Flow<List<WebFilterStats>> = 
        webFilterDao.getWebFilterStatsByChildIdFlow(childId)
    
    suspend fun getWebFilterStatsByChildIdAndDate(childId: String, date: Date): WebFilterStats? = 
        webFilterDao.getWebFilterStatsByChildIdAndDate(childId, date)
    
    suspend fun getWebFilterStatsByChildIdAndDateRange(childId: String, startDate: Date, endDate: Date): List<WebFilterStats> = 
        webFilterDao.getWebFilterStatsByChildIdAndDateRange(childId, startDate, endDate)
    
    suspend fun insertWebFilterStats(stats: WebFilterStats) = webFilterDao.insertWebFilterStats(stats)
    
    suspend fun insertWebFilterStatsList(statsList: List<WebFilterStats>) = webFilterDao.insertWebFilterStatsList(statsList)
    
    suspend fun updateWebFilterStats(stats: WebFilterStats) = webFilterDao.updateWebFilterStats(stats)
    
    suspend fun deleteWebFilterStats(stats: WebFilterStats) = webFilterDao.deleteWebFilterStats(stats)
    
    suspend fun deleteWebFilterStatsById(id: String) = webFilterDao.deleteWebFilterStatsById(id)
    
    suspend fun updateWebFilterStatsCounts(id: String, totalRequests: Int, blockedRequests: Int, allowedRequests: Int) = 
        webFilterDao.updateWebFilterStatsCounts(id, totalRequests, blockedRequests, allowedRequests)
}