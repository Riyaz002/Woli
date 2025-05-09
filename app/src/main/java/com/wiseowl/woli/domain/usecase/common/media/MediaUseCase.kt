package com.wiseowl.woli.domain.usecase.common.media

import com.wiseowl.woli.domain.repository.media.MediaRepository
import com.wiseowl.woli.domain.repository.media.model.Page
import com.wiseowl.woli.domain.repository.media.model.Photo


class MediaUseCase(
    val getPageUseCase: GetPageUseCase,
    val geSearchUseCase: GetSearchUseCase,
    val getPhotoUseCase: GetPhotoUseCase,
)

class GetPageUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(pageNo: Int): Page = repository.getPage(pageNo)
}

class GetSearchUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(query: String, pageNo: Int): Page = repository.getSearch(query ,pageNo)
}

class GetPhotoUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(photoId: Int): Photo = repository.getPhoto(photoId)
}