package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.Image

interface PageRepository {
    suspend fun getPage(page: Int): List<Image>?
    suspend fun getTotalPageCount(): Int
}