package com.wiseowl.woli.domain

import com.wiseowl.woli.domain.model.Image

interface WoliRepository {
    suspend fun getPage(page: Int): List<Image>?
    suspend fun getTotalPageCount(): Int
    suspend fun getImage(id: Int): Image?
    suspend fun getImages(category: String): List<Image>?
}