package com.wiseowl.woli.domain.usecase.detail

import coil3.Bitmap
import com.wiseowl.woli.domain.repository.ImageRepository

class GetBitmapUseCase(private val repository: ImageRepository) {
    suspend operator fun invoke(url: String?): Bitmap?{
        if(url==null) return null
        return repository.getImageBitmap(url)
    }
}