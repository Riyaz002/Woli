package com.wiseowl.woli.domain.usecase.detail

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.view.WindowManager
import coil3.Bitmap
import com.wiseowl.woli.R
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventHandler
import com.wiseowl.woli.domain.usecase.util.bitmapToUri
import com.wiseowl.woli.domain.usecase.util.centerCropBitmap
import com.wiseowl.woli.domain.usecase.util.getScreenProperties

class SetWallpaperUseCase(
    private val context: Context
) {
    private val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
    private val windowManager: WindowManager = context.getSystemService(WindowManager::class.java)

    operator fun invoke(bitmap: Bitmap, setType: SetWallpaperType){
        val screenProperties = getScreenProperties(windowManager)
        when(setType){
            SetWallpaperType.ONLY_HOME -> wallpaperManager.setBitmap(centerCropBitmap(bitmap, screenProperties.width, screenProperties.height), null, true, WallpaperManager.FLAG_SYSTEM)
            SetWallpaperType.ONLY_LOCK -> wallpaperManager.setBitmap(centerCropBitmap(bitmap, screenProperties.width, screenProperties.height), null, true, WallpaperManager.FLAG_LOCK)
            SetWallpaperType.FOR_BOTH -> wallpaperManager.setBitmap(centerCropBitmap(bitmap, screenProperties.width, screenProperties.height), null, true, WallpaperManager.FLAG_SYSTEM)
            SetWallpaperType.USE_OTHER_APP -> {
                val intent = Intent(Intent.ACTION_ATTACH_DATA).apply {
                    addCategory(Intent.CATEGORY_DEFAULT)
                    setDataAndType(bitmapToUri(context, bitmap), "image/*")
                    putExtra("mimeType", "image/*")
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
                val superIntent = Intent.createChooser(intent, context.getString(R.string.wallpaper_setter_choosing_dialog_title))
                EventHandler.sendEvent(Event.StartActivity(superIntent))
            }
        }
    }
}

enum class SetWallpaperType{
    ONLY_HOME,
    ONLY_LOCK,
    FOR_BOTH,
    USE_OTHER_APP
}