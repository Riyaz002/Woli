package com.wiseowl.woli.domain.repository

import coil3.Bitmap
import com.wiseowl.woli.domain.model.Image

interface ImageRepository {
    suspend fun getImageBitmap(url: String): Bitmap?
}