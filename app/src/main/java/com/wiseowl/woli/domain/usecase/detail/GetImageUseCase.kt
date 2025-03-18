package com.wiseowl.woli.domain.usecase.detail

import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.repository.ImageRepository

class GetImageUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(imageId: Int): Image? {
        return repository.getImage(imageId)
    }
}