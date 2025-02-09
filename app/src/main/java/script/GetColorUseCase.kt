package script

import android.graphics.Color
import coil3.Bitmap
import kotlin.math.pow
import kotlin.math.sqrt

class GetColorUseCase {

    operator fun invoke(image: Bitmap): com.wiseowl.woli.domain.model.Color{
        val color = getDominantColor(image)
        var secondaryColor = getSecondDominantColor(image)
        val areToClose = areColorsTooClose(color, secondaryColor)
        if(areToClose) secondaryColor = getFarAwayColor(color)
        return com.wiseowl.woli.domain.model.Color(color, secondaryColor)
    }

    private fun getDominantColor(image: Bitmap): Int{
        val newBitmap: Bitmap = Bitmap.createScaledBitmap(image, 1, 1, true)
        val color = newBitmap.getPixel(0, 0)
        newBitmap.recycle()
        return color
    }

    private fun getSecondDominantColor(bitmap: Bitmap): Int {
        val newBitmap: Bitmap = Bitmap.createScaledBitmap(bitmap, 2, 2, true)
        val color = newBitmap.getPixel(1, 1)
        newBitmap.recycle()
        return color
    }

    private fun areColorsTooClose(color1: Int, color2: Int, threshold: Double = 80.0): Boolean {
        val r1 = Color.red(color1)
        val g1 = Color.green(color1)
        val b1 = Color.blue(color1)

        val r2 = Color.red(color2)
        val g2 = Color.green(color2)
        val b2 = Color.blue(color2)

        // Euclidean distance in RGB space
        val distance = sqrt(
            (r1 - r2).toDouble().pow(2.0) +
                    (g1 - g2).toDouble().pow(2.0) +
                    (b1 - b2).toDouble().pow(2.0)
        )

        return distance < threshold
    }

    private fun getFarAwayColor(color: Int): Int{
        val r1 = Color.red(color)
        val g1 = Color.green(color)
        val b1 = Color.blue(color)

        val newR = (r1 + 255) / 2
        val newG = (g1 + 255) / 2
        val newB = (b1 + 255) / 2

        return Color.rgb(newR, newG, newB)
    }
}