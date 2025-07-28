package com.wiseowl.woli.domain.service

import com.wiseowl.woli.domain.model.DownloadRequest

fun interface DownloadManagerService {
    fun enqueue(request: DownloadRequest): Long
}