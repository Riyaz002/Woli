package com.wiseowl.woli.data.local.entity

import com.wiseowl.woli.domain.model.Category

data class CategoryDTO(
    val name: String,
    val cover: String
){
    companion object{
        fun CategoryDTO.toCategory(): Category = Category(
            name,
            cover
        )

        fun Category.toCategoriesDTO() = CategoryDTO(
            name,
            cover
        )
    }
}