package com.wiseowl.woli.data.repository

import coil3.Bitmap
import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteDataService
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.ImageRepository

class ImageRepository(private val remoteDataService: RemoteDataService): ImageRepository {
    override suspend fun getImage(id: Int): Image? {
        return remoteDataService.getImage(id)?.toImage()
    }

    override suspend fun getImageBitmap(url: String): Bitmap? {
        return remoteDataService.getImageBitmap(url)
    }

    override suspend fun getImages(category: String): List<Image>? {
        return remoteDataService.getImages(category)?.map { it.toImage() }
    }
}