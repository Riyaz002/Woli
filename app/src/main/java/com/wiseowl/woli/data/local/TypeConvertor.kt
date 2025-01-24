package com.wiseowl.woli.data.local

import androidx.room.TypeConverter

object TypeConvertor {
    @TypeConverter
    fun fromCategory(category: List<String>): String{
        return category.joinToString("#")
    }

    @TypeConverter
    fun toCategory(categoryString: String): List<String>{
        return categoryString.split("#")
    }
}