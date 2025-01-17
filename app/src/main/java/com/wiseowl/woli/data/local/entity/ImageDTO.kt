package com.wiseowl.woli.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wiseowl.woli.domain.model.Image

@Entity(tableName = "image")
data class ImageDTO(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val url: String,
    val description: String,
    val category: String
){
    companion object{
        fun ImageDTO.toImage(): Image = Image(
            id,
            url,
            description,
            category
        )
    }
}