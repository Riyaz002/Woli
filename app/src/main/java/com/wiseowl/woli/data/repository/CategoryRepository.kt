package com.wiseowl.woli.data.repository

import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteDataService
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.CategoryRepository

class CategoryRepository(private val remoteDataService: RemoteDataService): CategoryRepository {
    override suspend fun getCategoryImages(category: String): List<Image>? {
        return remoteDataService.getImages(category = category)?.map { it.toImage() }
    }
}