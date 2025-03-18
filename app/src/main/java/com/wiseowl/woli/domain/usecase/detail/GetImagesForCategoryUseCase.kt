package com.wiseowl.woli.domain.usecase.detail

import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.ImageRepository
import kotlin.collections.List

class GetImagesForCategoryUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(category: String): List<Image>? {
        return repository.getImages(category)
    }
}