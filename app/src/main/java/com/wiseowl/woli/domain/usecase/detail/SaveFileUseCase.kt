package com.wiseowl.woli.domain.usecase.detail

import android.app.DownloadManager
import android.net.Uri
import com.wiseowl.woli.domain.model.DownloadRequest
import com.wiseowl.woli.domain.service.DownloadManagerService

/**
 * Saves file using [DownloadManager].
 *
 * @param downloadManagerService The application context.
 *
 * @author Riyaz Uddin
 * @since version 11
 */
class SaveFileUseCase(private val downloadManagerService: DownloadManagerService) {

    /**
     * @param file Uri of the file to download.
     * @param destination Uri of the destination directory.
     * @param title title of the notification.
     * @param description description of the notification.
     */
    operator fun invoke(
        file: Uri,
        destination: Uri,
        title: String,
        description: String,
    ): Result<Long>{
        val id = downloadManagerService.enqueue(
            DownloadRequest(file, title, description, destination)
        )
        return Result.success(id)
    }
}
