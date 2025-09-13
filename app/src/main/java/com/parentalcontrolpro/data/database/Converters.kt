package com.parentalcontrolpro.data.database

import androidx.room.TypeConverter
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
    fun fromStringList(value: List<String>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let {
            val listType = object : TypeToken<List<String>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromIntList(value: List<Int>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toIntList(value: String?): List<Int>? {
        return value?.let {
            val listType = object : TypeToken<List<Int>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromMap(value: Map<String, Any>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toMap(value: String?): Map<String, Any>? {
        return value?.let {
            val mapType = object : TypeToken<Map<String, Any>>() {}.type
            gson.fromJson(it, mapType)
        }
    }
    
    @TypeConverter
    fun fromAppUsageList(value: List<AppUsage>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toAppUsageList(value: String?): List<AppUsage>? {
        return value?.let {
            val listType = object : TypeToken<List<AppUsage>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromWebActivityList(value: List<WebActivity>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toWebActivityList(value: String?): List<WebActivity>? {
        return value?.let {
            val listType = object : TypeToken<List<WebActivity>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromLocationDataList(value: List<LocationData>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toLocationDataList(value: String?): List<LocationData>? {
        return value?.let {
            val listType = object : TypeToken<List<LocationData>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromCallLogList(value: List<CallLog>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toCallLogList(value: String?): List<CallLog>? {
        return value?.let {
            val listType = object : TypeToken<List<CallLog>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromMessageList(value: List<Message>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toMessageList(value: String?): List<Message>? {
        return value?.let {
            val listType = object : TypeToken<List<Message>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromAlertList(value: List<Alert>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toAlertList(value: String?): List<Alert>? {
        return value?.let {
            val listType = object : TypeToken<List<Alert>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromSafeZoneList(value: List<SafeZone>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toSafeZoneList(value: String?): List<SafeZone>? {
        return value?.let {
            val listType = object : TypeToken<List<SafeZone>>() {}.type
            gson.fromJson(it, listType)
        }
    }
    
    @TypeConverter
    fun fromDangerZoneList(value: List<DangerZone>?): String? {
        return value?.let { gson.toJson(it) }
    }
    
    @TypeConverter
    fun toDangerZoneList(value: String?): List<DangerZone>? {
        return value?.let {
            val listType = object : TypeToken<List<DangerZone>>() {}.type
            gson.fromJson(it, listType)
        }
    }
}