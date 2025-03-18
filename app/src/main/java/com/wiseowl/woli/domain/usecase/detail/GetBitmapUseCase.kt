package com.wiseowl.woli.domain.usecase.detail

import coil3.Bitmap
import com.wiseowl.woli.domain.repository.ImageRepository

class GetBitmapUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(url: String): Bitmap?{
        return repository.getImageBitmap(url)
    }
}