package com.wiseowl.woli.domain

import com.wiseowl.woli.data.local.entity.ImageDTO

interface RemoteDataService {
    suspend fun getPage(page: Int): List<ImageDTO>?
    suspend fun getTotalPageCount(): Int
    suspend fun getImage(id: Int): ImageDTO?
    suspend fun getImages(category: String): List<ImageDTO>?
}