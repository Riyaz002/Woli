package com.wiseowl.woli.domain.usecase.categories

import com.wiseowl.woli.domain.model.Page
import com.wiseowl.woli.domain.model.Category
import com.wiseowl.woli.domain.repository.CategoryRepository

class PageUseCase(private val repository: CategoryRepository) {
    suspend fun getPage(pageNo: Int): Page<Category> {
        return Page(pageNo, repository.getCategoryPage(pageNo))
    }
}