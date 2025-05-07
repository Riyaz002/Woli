package com.wiseowl.woli.data.local.db

import androidx.room.TypeConverter
import com.wiseowl.woli.data.local.db.entity.ColorDTO

object TypeConvertor {
    @TypeConverter
    fun fromCategory(category: List<String>): String{
        return category.joinToString("#")
    }

    @TypeConverter
    fun toCategory(categoryString: String): List<String>{
        return categoryString.split("#")
    }

    @TypeConverter
    fun fromColorDTOString(color: ColorDTO): String{
        return listOf(
            color.primary.toString(),
            color.secondary.toString()
        ).joinToString("#")
    }

    @TypeConverter
    fun toColorDTO(colorDTOString: String): ColorDTO {
        val result = colorDTOString.split("#")
        val primary = result.first()
        val secondary = result.last()
        return ColorDTO(
            primary = primary.toInt(),
            secondary = secondary.toInt()
        )
    }
}