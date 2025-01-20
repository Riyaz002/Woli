package com.wiseowl.woli.domain

import com.wiseowl.woli.data.local.entity.ImageDTO

interface RemoteDataService {
    suspend fun getPage(page: Int): List<ImageDTO>?
    suspend fun getImage(id: Int): ImageDTO?
}