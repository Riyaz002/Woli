package com.wiseowl.woli.data.repository

import coil3.Bitmap
import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.ImageRepository

class ImageRepository(private val apiService: RemoteAPIService): ImageRepository {
    override suspend fun getImage(id: Int): Image? {
        return apiService.getImage(id)?.toImage()
    }

    override suspend fun getImageBitmap(url: String): Bitmap? {
        return apiService.getImageBitmap(url)
    }

    override suspend fun getImages(category: String): List<Image>? {
        return apiService.getImages(category)?.map { it.toImage() }
    }
}