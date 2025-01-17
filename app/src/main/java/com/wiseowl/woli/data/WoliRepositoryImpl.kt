package com.wiseowl.woli.data

import com.wiseowl.woli.data.coroutine.Dispatcher
import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteDataService
import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Image
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.withContext

class WoliRepositoryImpl(private val remoteDataService: RemoteDataService): WoliRepository {
    override suspend fun getPage(page: Int): List<Image>? {
        return withContext(Dispatcher.IO){
            return@withContext remoteDataService.getPage(page)?.map { it.toImage() }
        }
    }
}