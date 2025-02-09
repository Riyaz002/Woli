package com.wiseowl.woli.domain.usecase.util

import android.os.Build
import android.util.DisplayMetrics
import android.view.WindowManager

fun getScreenProperties(windowManager: WindowManager): ScreenProperties {
    val properties = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val bounds = windowManager.currentWindowMetrics.bounds
        return ScreenProperties(bounds.width(), bounds.height())
    } else {
        val displayMetrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        ScreenProperties(displayMetrics.widthPixels, displayMetrics.heightPixels)
    }
    return properties
}

class ScreenProperties(
    val width: Int,
    val height: Int
)