package com.wiseowl.woli.domain.usecase.detail

import android.app.WallpaperManager
import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager
import coil3.Bitmap
import kotlin.math.max

class SetWallpaperUseCase(private val wallpaperManager: WallpaperManager, private val windowManager: WindowManager) {
    operator fun invoke(bitmap: Bitmap){
        val (width, height) = getScreenSize()
        wallpaperManager.setBitmap(centerCropBitmap(bitmap, width,height))
    }

    private fun getScreenSize(): Pair<Int, Int> {
        val windowManager = windowManager
        val size = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val bounds = windowManager.currentWindowMetrics.bounds
            return Pair(bounds.width(), bounds.height())
        } else {
            val displayMetrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(displayMetrics)
            Pair(displayMetrics.widthPixels, displayMetrics.heightPixels)
        }
        return size
    }

    private fun centerCropBitmap(source: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
        val sourceWidth = source.width
        val sourceHeight = source.height

        // Calculate the scaling factors to maintain the aspect ratio
        val scale = max(targetWidth.toFloat() / sourceWidth, targetHeight.toFloat() / sourceHeight)

        // Calculate the new dimensions after scaling
        val scaledWidth = (scale * sourceWidth).toInt()
        val scaledHeight = (scale * sourceHeight).toInt()

        // Scale the bitmap
        val scaledBitmap = Bitmap.createScaledBitmap(source, scaledWidth, scaledHeight, true)

        // Calculate the cropping starting point (to crop equally from both sides)
        val xOffset = (scaledWidth - targetWidth) / 2
        val yOffset = (scaledHeight - targetHeight) / 2

        // Crop the bitmap
        return Bitmap.createBitmap(scaledBitmap, xOffset, yOffset, targetWidth, targetHeight)
    }

}