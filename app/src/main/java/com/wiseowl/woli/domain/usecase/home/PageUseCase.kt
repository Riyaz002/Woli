package com.wiseowl.woli.domain.usecase.home

import com.wiseowl.woli.domain.model.Page
import com.wiseowl.woli.data.repository.PageRepository
import com.wiseowl.woli.domain.model.Image

class PageUseCase(private val repository: PageRepository) {
    suspend fun getPage(pageNo: Int): Page<Image> {
        return Page(pageNo, repository.getPage(pageNo))
    }
    suspend fun getTotalPageCount(): Int{
        return repository.getTotalPageCount()
    }
}