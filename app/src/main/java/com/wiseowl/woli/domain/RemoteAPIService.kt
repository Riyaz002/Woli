package com.wiseowl.woli.domain

import coil3.Bitmap
import com.wiseowl.woli.data.local.entity.CategoryDTO
import com.wiseowl.woli.data.local.entity.ImageDTO

interface RemoteAPIService {
    suspend fun getPage(page: Int): List<ImageDTO>?
    suspend fun getTotalPageCount(): Int
    suspend fun getImage(id: Int): ImageDTO?
    suspend fun getImages(category: String): List<ImageDTO>?
    suspend fun getImageBitmap(url: String): Bitmap?
    suspend fun getCategories(): List<CategoryDTO>?
}