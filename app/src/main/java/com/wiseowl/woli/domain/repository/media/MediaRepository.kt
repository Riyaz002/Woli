package com.wiseowl.woli.domain.repository.media

import com.wiseowl.woli.domain.repository.media.model.Page
import com.wiseowl.woli.domain.repository.media.model.Photo

interface MediaRepository {
    fun getPage(pageNo: Int): Page
    fun getSearch(query: String, pageNo: Int): Page
    fun getPhoto(photoId: Int): Photo
}