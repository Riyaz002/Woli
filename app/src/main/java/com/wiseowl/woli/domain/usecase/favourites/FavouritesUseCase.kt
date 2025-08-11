package com.wiseowl.woli.domain.usecase.favourites

import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class FavouritesUseCase(
    private val accountUseCase: AccountUseCase,
    private val mediaUseCase: MediaUseCase
) {
    suspend fun addToFavourites(mediaId: Long){
        return accountUseCase.addToFavourites(mediaId)
    }

    suspend fun getFavourites(): List<Media>{
        return accountUseCase.getFavourites().map { withContext(Dispatcher.IO){ async { mediaUseCase.getPhotoUseCase(it.toInt()) }.await() } }
    }

    suspend fun removeFromFavourites(mediaId: Long){
        return accountUseCase.removeFromFavourites(mediaId)
    }
}