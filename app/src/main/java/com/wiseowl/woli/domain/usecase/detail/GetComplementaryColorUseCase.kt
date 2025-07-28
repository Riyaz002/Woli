package com.wiseowl.woli.domain.usecase.detail

import android.graphics.Color

class GetComplementaryColorUseCase() {
    operator fun invoke(color: Int): Int{
        val red = Color.red(color)
        val green = Color.green(color)
        val blue = Color.blue(color)

        val complementaryRed = 255 - red
        val complementaryGreen = 255 - green
        val complementaryBlue = 255 - blue

        return Color.rgb(complementaryRed, complementaryGreen, complementaryBlue)
    }
}