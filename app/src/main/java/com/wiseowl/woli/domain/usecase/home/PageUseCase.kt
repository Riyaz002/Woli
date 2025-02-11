package com.wiseowl.woli.domain.usecase.home

import com.wiseowl.woli.domain.model.Page
import com.wiseowl.woli.data.repository.PageRepository

class PageUseCase(private val repository: PageRepository) {
    suspend fun getPage(pageNo: Int): Page {
        return Page(pageNo, repository.getPage(pageNo))
    }
    suspend fun getTotalPageCount(): Int{
        return repository.getTotalPageCount()
    }
}