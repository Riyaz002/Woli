package com.wiseowl.woli.domain

import com.wiseowl.woli.domain.model.Image

interface WoliRepository {
    suspend fun getPage(page: Int): List<Image>?
    suspend fun getImage(id: Int): Image?
}