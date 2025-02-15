package com.wiseowl.woli.data.repository

import com.wiseowl.woli.data.local.entity.ImageDTO.Companion.toImage
import com.wiseowl.woli.domain.RemoteAPIService
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.PageRepository

class PageRepository(private val apiService: RemoteAPIService): PageRepository {
    override suspend fun getPage(page: Int): List<Image>? {
        return apiService.getPage(page)?.map { it.toImage() }
    }

    override suspend fun getTotalPageCount(): Int {
        return apiService.getTotalPageCount()
    }
}