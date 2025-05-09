package com.wiseowl.woli.domain.service.mediaprovider

import com.wiseowl.woli.domain.service.mediaprovider.model.Page
import com.wiseowl.woli.domain.service.mediaprovider.model.Photo

interface MediaProviderService {
    suspend fun search(query: String, pageNo: Int): Page
    suspend fun getPage(pageNo: Int): Page
    suspend fun getPhoto(id: Int): Photo
}