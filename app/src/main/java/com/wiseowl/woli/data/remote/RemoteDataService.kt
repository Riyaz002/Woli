package com.wiseowl.woli.data.remote

import com.wiseowl.woli.data.local.entity.ImageDTO

interface RemoteDataService {
    suspend fun getPage(page: Int): List<ImageDTO>?
}