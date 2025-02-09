package com.wiseowl.woli.data

import android.content.Context
import coil3.Bitmap
import coil3.ImageLoader
import coil3.request.ImageRequest
import coil3.request.SuccessResult
import coil3.request.allowHardware
import coil3.toBitmap
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteDataService
import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Image
import kotlinx.coroutines.withContext

class WoliRepositoryImpl(private val remoteDataService: RemoteDataService): WoliRepository {
    override suspend fun getPage(page: Int): List<Image>? {
        return withContext(Dispatcher.IO){
            return@withContext remoteDataService.getPage(page)?.map { it.toImage() }
        }
    }

    override suspend fun getTotalPageCount(): Int {
        return withContext(Dispatcher.IO){
            return@withContext remoteDataService.getTotalPageCount()
        }
    }

    override suspend fun getImage(id: Int): Image? {
        return withContext(Dispatcher.IO){
            return@withContext remoteDataService.getImage(id)?.toImage()
        }
    }

    override suspend fun getImageBitmap(context: Context, id: Int): Bitmap? {
        return withContext(Dispatcher.IO){
            val image = getImage(id)
            val loader = ImageLoader(context)
            val request = ImageRequest.Builder(context)
                .data(image?.url)
                .allowHardware(false) // Disable hardware bitmaps.
                .build()

            val result = (loader.execute(request))
            result.image?.toBitmap()
        }
    }

    override suspend fun getImages(category: String): List<Image>? {
        return withContext(Dispatcher.IO){
            return@withContext remoteDataService.getImages(category)?.map { it.toImage() }
        }
    }
}