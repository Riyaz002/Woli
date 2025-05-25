package com.wiseowl.woli.domain.service

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import com.wiseowl.woli.domain.model.DownloadRequest

class DownloadManagerServiceImpl(context: Context) : DownloadManagerService {
    private val downloadManager = context.getSystemService(DownloadManager::class.java)
    override fun enqueue(request: DownloadRequest): Long {
        val request = createDownloadRequest(request)
        return downloadManager.enqueue(request)
    }

    private fun createDownloadRequest(downloadRequest: DownloadRequest): DownloadManager.Request{
        return DownloadManager.Request(downloadRequest.file)
            .setTitle(getFileName(downloadRequest.file))
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, getFileName(downloadRequest.file))
    }

    private fun getFileName(uri: Uri): String? {
        return uri.path?.split("/")?.lastOrNull()?.substringBefore("?")
    }
}