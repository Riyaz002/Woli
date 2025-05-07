package com.wiseowl.woli.util

import android.content.Intent
import com.wiseowl.woli.ui.navigation.Screen

class DeepLinkParser() {
    fun getPage(intent: Intent): Result<Screen?> {
        val path = intent.data?.path
        val screenId = path?.removePrefix("/")?.split("/")?.firstOrNull() ?: return Result.failure(NullPointerException("Invalid or null URL: ${intent.data}"))
        return Result.success(Screen.fromRoute(screenId.uppercase()))
    }
}