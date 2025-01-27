package com.wiseowl.woli.domain.usecase.detail

import android.app.WallpaperManager
import coil3.Bitmap

class SetWallpaperUseCase(private val wallpaperManager: WallpaperManager) {
    operator fun invoke(bitmap: Bitmap){
        wallpaperManager.setBitmap(bitmap)
    }
}