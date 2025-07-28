package com.wiseowl.woli.domain.repository

import coil3.Bitmap

interface ImageRepository {
    suspend fun getImageBitmap(url: String): Bitmap?
}