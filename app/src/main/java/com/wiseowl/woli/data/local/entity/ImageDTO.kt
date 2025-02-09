package com.wiseowl.woli.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.wiseowl.woli.data.local.TypeConvertor
import com.wiseowl.woli.data.local.entity.ColorDTO.Companion.toColor
import com.wiseowl.woli.data.local.entity.ColorDTO.Companion.toColorDTO
import com.wiseowl.woli.domain.model.Image

@TypeConverters(TypeConvertor::class)
@Entity(tableName = "image")
data class ImageDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val description: String,
    val categories: List<String>,
    var color: ColorDTO?
){
    companion object{
        fun ImageDTO.toImage(): Image = Image(
            id,
            url,
            description,
            categories,
            color?.toColor()
        )

        fun Image.toImageDTO(): ImageDTO = ImageDTO(
            id,
            url,
            description,
            categories,
            color?.toColorDTO()
        )
    }
}