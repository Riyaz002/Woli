package com.wiseowl.woli.data.repository

import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.CategoryRepository

class CategoryRepository(private val apiService: RemoteAPIService): CategoryRepository {
    override suspend fun getCategoryImages(category: String): List<Image>? {
        return apiService.getImages(category = category)?.map { it.toImage() }
    }
}