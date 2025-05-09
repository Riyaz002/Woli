package com.wiseowl.woli.domain.repository.media

import com.wiseowl.woli.domain.repository.media.model.Page
import com.wiseowl.woli.domain.repository.media.model.Photo

interface MediaRepository {
    suspend fun getPage(pageNo: Int): Page
    suspend fun getSearch(query: String, pageNo: Int): Page
    suspend fun getPhoto(photoId: Int): Photo
}