package com.wiseowl.woli.domain.usecase.detail

import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Image
import kotlin.collections.List

class GetImagesForCategoryUseCase(private val repository: WoliRepository) {
    suspend operator fun invoke(category: String): List<Image>? {
        return repository.getImages(category)
    }
}