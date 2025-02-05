package com.wiseowl.woli.domain.usecase.detail

import coil3.Bitmap

class GetImageDominantColorUseCase {
    operator fun invoke(image: Bitmap): Int{
        val newBitmap: Bitmap = Bitmap.createScaledBitmap(image, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }
}