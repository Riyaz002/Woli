package com.wiseowl.woli.domain.repository

import coil3.Bitmap
import com.wiseowl.woli.domain.model.Image

interface ImageRepository {
    suspend fun getImage(id: Int): Image?
    suspend fun getImageBitmap(url: String): Bitmap?
    suspend fun getImages(category: String): List<Image>?
}