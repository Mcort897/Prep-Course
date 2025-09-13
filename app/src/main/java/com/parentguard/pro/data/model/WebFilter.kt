package com.parentguard.pro.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.util.Date

@Entity(tableName = "web_filters")
@Parcelize
data class WebFilter(
    @PrimaryKey
    val id: String,
    val childId: String,
    val name: String,
    val description: String? = null,
    val isActive: Boolean = true,
    val createdAt: Date = Date(),
    val lastModifiedAt: Date = Date(),
    val parentId: String,
    val rules: List<WebFilterRule> = emptyList()
) : Parcelable

@Entity(tableName = "web_filter_rules")
@Parcelize
data class WebFilterRule(
    @PrimaryKey
    val id: String,
    val webFilterId: String,
    val ruleType: WebFilterRuleType,
    val pattern: String,
    val isBlocked: Boolean = true,
    val isRegex: Boolean = false,
    val caseSensitive: Boolean = false,
    val description: String? = null,
    val createdAt: Date = Date(),
    val isActive: Boolean = true
) : Parcelable

@Parcelize
enum class WebFilterRuleType : Parcelable {
    URL_CONTAINS,
    URL_EQUALS,
    URL_STARTS_WITH,
    URL_ENDS_WITH,
    DOMAIN_CONTAINS,
    DOMAIN_EQUALS,
    DOMAIN_STARTS_WITH,
    DOMAIN_ENDS_WITH,
    KEYWORD_IN_TITLE,
    KEYWORD_IN_CONTENT,
    CATEGORY_FILTER,
    CUSTOM_REGEX
}

@Entity(tableName = "web_filter_categories")
@Parcelize
data class WebFilterCategory(
    @PrimaryKey
    val id: String,
    val name: String,
    val description: String? = null,
    val keywords: List<String> = emptyList(),
    val domains: List<String> = emptyList(),
    val isActive: Boolean = true,
    val createdAt: Date = Date()
) : Parcelable

@Entity(tableName = "web_filter_violations")
@Parcelize
data class WebFilterViolation(
    @PrimaryKey
    val id: String,
    val childId: String,
    val webFilterId: String,
    val webFilterRuleId: String,
    val url: String,
    val title: String? = null,
    val domain: String,
    val violatedAt: Date = Date(),
    val isBlocked: Boolean = true,
    val userAgent: String? = null,
    val referrer: String? = null,
    val violationType: WebFilterViolationType,
    val severity: ViolationSeverity = ViolationSeverity.MEDIUM,
    val isResolved: Boolean = false,
    val resolvedAt: Date? = null,
    val resolutionNotes: String? = null
) : Parcelable

@Parcelize
enum class WebFilterViolationType : Parcelable {
    BLOCKED_URL_ACCESS,
    BLOCKED_DOMAIN_ACCESS,
    BLOCKED_KEYWORD_FOUND,
    BLOCKED_CATEGORY_ACCESS,
    SUSPICIOUS_ACTIVITY,
    ADULT_CONTENT,
    VIOLENCE,
    DRUGS,
    GAMBLING,
    SOCIAL_MEDIA,
    GAMING,
    SHOPPING,
    NEWS,
    ENTERTAINMENT
}

@Parcelize
enum class ViolationSeverity : Parcelable {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

@Entity(tableName = "web_filter_stats")
@Parcelize
data class WebFilterStats(
    @PrimaryKey
    val id: String,
    val childId: String,
    val date: Date,
    val totalRequests: Int = 0,
    val blockedRequests: Int = 0,
    val allowedRequests: Int = 0,
    val violationsByType: Map<WebFilterViolationType, Int> = emptyMap(),
    val violationsBySeverity: Map<ViolationSeverity, Int> = emptyMap(),
    val topBlockedDomains: List<String> = emptyList(),
    val topBlockedKeywords: List<String> = emptyList(),
    val mostActiveHours: List<Int> = emptyList()
) : Parcelable