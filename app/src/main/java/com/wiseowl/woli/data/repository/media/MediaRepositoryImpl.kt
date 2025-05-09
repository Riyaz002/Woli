package com.wiseowl.woli.data.repository.media

import com.wiseowl.woli.data.remote.media.PexelsAPIService
import com.wiseowl.woli.domain.repository.media.MediaRepository
import com.wiseowl.woli.domain.repository.media.model.Page
import com.wiseowl.woli.domain.repository.media.model.Photo

class MediaRepositoryImpl(private val apiService: PexelsAPIService): MediaRepository {
    override suspend fun getPage(pageNo: Int): Page {
        return apiService.getPage(pageNo).execute().body()!!.toPage()
    }

    override suspend fun getSearch(query: String, pageNo: Int): Page {
        return apiService.getSearch(query, pageNo).execute().body()!!.toPage()
    }

    override suspend fun getPhoto(photoId: Int): Photo {
        return apiService.getPhoto(photoId).execute().body()!!.toPhoto()
    }
}