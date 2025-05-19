package com.wiseowl.woli.domain.repository.media

import com.wiseowl.woli.domain.repository.media.model.CollectionPage
import com.wiseowl.woli.domain.repository.media.model.PhotoPage
import com.wiseowl.woli.domain.repository.media.model.Media

interface MediaRepository {
    suspend fun getPage(pageNo: Int): PhotoPage
    suspend fun getSearch(query: String, pageNo: Int): PhotoPage
    suspend fun getPhoto(photoId: Int): Media
    suspend fun getCollections(pageNo: Int): CollectionPage
    suspend fun getCollection(id: String, pageNo: Int): List<Media>
}