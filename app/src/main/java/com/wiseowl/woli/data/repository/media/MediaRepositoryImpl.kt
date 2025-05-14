package com.wiseowl.woli.data.repository.media

import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.data.remote.media.PexelsAPIService
import com.wiseowl.woli.domain.repository.media.MediaRepository
import com.wiseowl.woli.domain.repository.media.model.CollectionPage
import com.wiseowl.woli.domain.repository.media.model.PhotoPage
import com.wiseowl.woli.domain.repository.media.model.Media
import kotlinx.coroutines.withContext

class MediaRepositoryImpl(private val apiService: PexelsAPIService): MediaRepository {
    override suspend fun getPage(pageNo: Int): PhotoPage {
        return withContext(Dispatcher.IO) {
            return@withContext apiService.getPage(pageNo).execute().body()!!.toPage()
        }
    }

    override suspend fun getSearch(query: String, pageNo: Int): PhotoPage {
        return withContext(Dispatcher.IO) {
            return@withContext apiService.getSearch(query, pageNo).execute().body()!!.toPage()
        }
    }

    override suspend fun getPhoto(photoId: Int): Media {
        return withContext(Dispatcher.IO) {
            return@withContext apiService.getPhoto(photoId).execute().body()!!.toMedia()
        }
    }

    override suspend fun getCollections(pageNo: Int): CollectionPage {
        return withContext(Dispatcher.IO) {
            return@withContext apiService.getCollections(pageNo).execute().body()!!.toCollectionPage()
        }
    }

    override suspend fun getCollection(id: String): List<Media> {
        return withContext(Dispatcher.IO) {
            return@withContext apiService.getCollection(id).execute().body()!!.media?.map { it.toMedia() }.orEmpty()
        }
    }
}