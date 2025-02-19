package com.wiseowl.woli.data.repository

import com.wiseowl.woli.data.local.entity.CategoryDTO.Companion.toCategory
import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.Category
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.CategoryRepository

class CategoryRepository(private val apiService: RemoteAPIService): CategoryRepository {
    override suspend fun getCategoryImages(category: String): List<Image>? {
        return apiService.getImages(category = category)?.map { it.toImage() }
    }

    override suspend fun getCategories(): List<Category>? {
        return apiService.getCategories()?.map { it.toCategory() }
    }
}