package com.wiseowl.woli.domain.usecase.detail

import android.graphics.Color
import coil3.Bitmap

class ColorUseCase {
    fun getDominantColor(image: Bitmap): Int{
        val newBitmap: Bitmap = Bitmap.createScaledBitmap(image, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    fun getComplementaryColor(color: Int): Int {
        val red = 255 - Color.red(color)
        val green = 255 - Color.green(color)
        val blue = 255 - Color.blue(color)
        return Color.rgb(red, green, blue)
    }
}