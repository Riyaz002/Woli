package com.wiseowl.woli.domain.usecase.home

import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Page

class PageUseCase(private val repository: WoliRepository) {
    suspend fun getPage(pageNo: Int): Page {
        return Page(pageNo, repository.getPage(pageNo))
    }
    suspend fun getTotalPageCount(): Int{
        return repository.getTotalPageCount()
    }
}