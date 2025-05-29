package com.wiseowl.woli.service

import com.wiseowl.woli.domain.model.DownloadRequest
import com.wiseowl.woli.domain.service.DownloadManagerService

class FakeDownloadManagerService: DownloadManagerService {
    private val enqueuedRequests = mutableListOf<Long>()
    override fun enqueue(request: DownloadRequest): Long {
        val id = request.toString().hashCode().toLong()
        enqueuedRequests.add(id)
        return id
    }
}