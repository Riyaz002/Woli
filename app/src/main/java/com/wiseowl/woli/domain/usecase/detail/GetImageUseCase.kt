package com.wiseowl.woli.domain.usecase.detail

import com.wiseowl.woli.domain.WoliRepository
import com.wiseowl.woli.domain.model.Image

class GetImageUseCase(private val repository: WoliRepository) {
    suspend operator fun invoke(imageId: Int): Image? {
        return repository.getImage(imageId)
    }
}