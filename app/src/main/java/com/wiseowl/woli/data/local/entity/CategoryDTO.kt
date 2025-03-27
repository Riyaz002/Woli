package com.wiseowl.woli.data.local.entity

import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImageDTO
import com.wiseowl.woli.domain.model.Category

data class CategoryDTO(
    val name: String,
    val cover: ImageDTO
){
    companion object{
        fun CategoryDTO.toCategory(): Category = Category(
            name,
            cover.toImage()
        )

        fun Category.toCategoriesDTO() = CategoryDTO(
            name,
            cover.toImageDTO()
        )
    }
}