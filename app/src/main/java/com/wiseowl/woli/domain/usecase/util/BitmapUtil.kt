package com.wiseowl.woli.domain.usecase.util

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import coil3.Bitmap
import java.io.File
import kotlin.math.max

fun getCenterCroppedBitmap(source: Bitmap, targetWidth: Int, targetHeight: Int): Bitmap {
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

fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    return try {
        // Create a file in the cache directory
        val file = File(context.cacheDir, "wallpaperBitmap.png")
        file.outputStream().use { outputStream ->
            bitmap.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, outputStream)
        }

        // Generate a Uri using FileProvider
        FileProvider.getUriForFile(
            context,
            "com.wiseowl.woli.provider", // Ensure this matches your manifest
            file
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}