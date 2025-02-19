package com.wiseowl.woli.domain.usecase.categories

import com.wiseowl.woli.domain.model.Category
import com.wiseowl.woli.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val repository: CategoryRepository) {
    suspend operator fun invoke(): List<Category>?{
        return repository.getCategories()
    }
}