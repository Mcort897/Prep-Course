package com.parentguard.pro.data.database

import androidx.room.TypeConverter
import com.parentguard.pro.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

class Converters {
    
    private val gson = Gson()

    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromUserType(type: UserType): String {
        return type.name
    }

    @TypeConverter
    fun toUserType(type: String): UserType {
        return UserType.valueOf(type)
    }

    @TypeConverter
    fun fromAppCategory(category: AppCategory): String {
        return category.name
    }

    @TypeConverter
    fun toAppCategory(category: String): AppCategory {
        return AppCategory.valueOf(category)
    }

    @TypeConverter
    fun fromTimeLimitType(type: TimeLimitType): String {
        return type.name
    }

    @TypeConverter
    fun toTimeLimitType(type: String): TimeLimitType {
        return TimeLimitType.valueOf(type)
    }

    @TypeConverter
    fun fromViolationType(type: ViolationType): String {
        return type.name
    }

    @TypeConverter
    fun toViolationType(type: String): ViolationType {
        return ViolationType.valueOf(type)
    }

    @TypeConverter
    fun fromResolutionType(type: ResolutionType?): String? {
        return type?.name
    }

    @TypeConverter
    fun toResolutionType(type: String?): ResolutionType? {
        return type?.let { ResolutionType.valueOf(it) }
    }

    @TypeConverter
    fun fromUserSettings(settings: UserSettings?): String? {
        return settings?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toUserSettings(settings: String?): UserSettings? {
        return settings?.let { gson.fromJson(it, UserSettings::class.java) }
    }

    @TypeConverter
    fun fromLocation(location: Location?): String? {
        return location?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toLocation(location: String?): Location? {
        return location?.let { gson.fromJson(it, Location::class.java) }
    }

    @TypeConverter
    fun fromDeviceInfo(deviceInfo: DeviceInfo?): String? {
        return deviceInfo?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toDeviceInfo(deviceInfo: String?): DeviceInfo? {
        return deviceInfo?.let { gson.fromJson(it, DeviceInfo::class.java) }
    }

    @TypeConverter
    fun fromChildRestrictions(restrictions: ChildRestrictions?): String? {
        return restrictions?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toChildRestrictions(restrictions: String?): ChildRestrictions? {
        return restrictions?.let { gson.fromJson(it, ChildRestrictions::class.java) }
    }

    @TypeConverter
    fun fromLocationRestrictionList(restrictions: List<LocationRestriction>?): String? {
        return restrictions?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toLocationRestrictionList(restrictions: String?): List<LocationRestriction>? {
        return restrictions?.let { 
            gson.fromJson(it, object : TypeToken<List<LocationRestriction>>() {}.type)
        }
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toStringList(list: String?): List<String>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<String>>() {}.type)
        }
    }

    @TypeConverter
    fun fromAppUsageList(list: List<AppUsage>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAppUsageList(list: String?): List<AppUsage>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<AppUsage>>() {}.type)
        }
    }

    @TypeConverter
    fun fromAppUsageDailyList(list: List<AppUsageDaily>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAppUsageDailyList(list: String?): List<AppUsageDaily>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<AppUsageDaily>>() {}.type)
        }
    }

    @TypeConverter
    fun fromAppUsageWeeklyList(list: List<AppUsageWeekly>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAppUsageWeeklyList(list: String?): List<AppUsageWeekly>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<AppUsageWeekly>>() {}.type)
        }
    }

    @TypeConverter
    fun fromAppUsageMonthlyList(list: List<AppUsageMonthly>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAppUsageMonthlyList(list: String?): List<AppUsageMonthly>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<AppUsageMonthly>>() {}.type)
        }
    }

    @TypeConverter
    fun fromIntList(list: List<Int>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toIntList(list: String?): List<Int>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<Int>>() {}.type)
        }
    }

    @TypeConverter
    fun fromAppCategoryMap(map: Map<AppCategory, Long>?): String? {
        return map?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAppCategoryMap(map: String?): Map<AppCategory, Long>? {
        return map?.let { 
            gson.fromJson(it, object : TypeToken<Map<AppCategory, Long>>() {}.type)
        }
    }

    @TypeConverter
    fun fromAppCategoryList(list: List<AppCategory>?): String? {
        return list?.let { gson.toJson(it) }
    }

    @TypeConverter
    fun toAppCategoryList(list: String?): List<AppCategory>? {
        return list?.let { 
            gson.fromJson(it, object : TypeToken<List<AppCategory>>() {}.type)
        }
    }
}