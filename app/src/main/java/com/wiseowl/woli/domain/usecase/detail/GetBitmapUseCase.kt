package com.wiseowl.woli.domain.usecase.detail

import android.content.Context
import coil3.Bitmap
import com.wiseowl.woli.domain.WoliRepository

class GetBitmapUseCase(private val context: Context, private val repository: WoliRepository) {
    suspend operator fun invoke(url: String): Bitmap?{
        return repository.getImageBitmap(context, url)
    }
}