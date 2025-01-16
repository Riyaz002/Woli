package com.wiseowl.woli.domain

import com.wiseowl.woli.domain.model.Image

interface ApiService {
    suspend fun getPage(): List<Image>
}