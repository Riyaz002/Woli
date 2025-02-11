package com.wiseowl.woli.data.repository

import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteDataService
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.PageRepository

class PageRepository(private val remoteDataService: RemoteDataService): PageRepository {
    override suspend fun getPage(page: Int): List<Image>? {
        return remoteDataService.getPage(page)?.map { it.toImage() }
    }

    override suspend fun getTotalPageCount(): Int {
        return remoteDataService.getTotalPageCount()
    }
}