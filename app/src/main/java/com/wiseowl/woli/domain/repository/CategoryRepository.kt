package com.wiseowl.woli.domain.repository

import com.wiseowl.woli.domain.model.Image

interface CategoryRepository {
    suspend fun getCategoryImages(category: String): List<Image>?
}