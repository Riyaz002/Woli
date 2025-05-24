package com.wiseowl.woli.domain.model

import android.net.Uri

data class DownloadRequest(
    val file: Uri,
    val title: String,
    val description: String,
    val destination: Uri,
)