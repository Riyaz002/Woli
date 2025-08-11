package com.wiseowl.woli.domain.usecase.common.media

import com.wiseowl.woli.domain.repository.media.MediaRepository
import com.wiseowl.woli.domain.repository.media.model.CollectionPage
import com.wiseowl.woli.domain.repository.media.model.PhotoPage
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.domain.usecase.account.AccountUseCase


class MediaUseCase(
    val getPhotoPageUseCase: GetPhotoPageUseCase,
    val geSearchUseCase: GetSearchUseCase,
    val getPhotoUseCase: GetPhotoUseCase,
    val getCollectionsPageUseCase: GetCollectionsPageUseCase,
    val getCollectionPageUseCase: GetCollectionPageUseCase
)

class GetPhotoPageUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(pageNo: Int): PhotoPage = repository.getPage(pageNo)
}

class GetSearchUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(query: String, pageNo: Int): PhotoPage = repository.getSearch(query ,pageNo)
}

class GetPhotoUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(photoId: Int): Media = repository.getPhoto(photoId)
}

class GetCollectionsPageUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(photoId: Int): CollectionPage = repository.getCollections(photoId)
}

class GetCollectionPageUseCase(private val repository: MediaRepository){
    suspend operator fun invoke(id: String, pageNo: Int): List<Media> = repository.getCollection(id, pageNo)
}