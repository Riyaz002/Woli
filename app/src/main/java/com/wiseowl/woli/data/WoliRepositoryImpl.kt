package com.wiseowl.woli.data

import com.wiseowl.woli.domain.ApiService
import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Image
import kotlinx.coroutines.CoroutineScope

class WoliRepositoryImpl(val apiService: ApiService): WoliRepository {
    val coroutineScope = CoroutineScope()
    override fun getPage(page: Int): List<Image>? {
        apiService.getPage()
    }
}