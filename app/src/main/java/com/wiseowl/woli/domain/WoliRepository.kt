package com.wiseowl.woli.domain

import android.content.Context
import coil3.Bitmap
import com.wiseowl.woli.domain.model.Image

interface WoliRepository {
    suspend fun getPage(page: Int): List<Image>?
    suspend fun getTotalPageCount(): Int
    suspend fun getImage(id: Int): Image?
    suspend fun getImageBitmap(context: Context, id: Int): Bitmap?
}