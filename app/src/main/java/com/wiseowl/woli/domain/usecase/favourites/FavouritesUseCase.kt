package com.wiseowl.woli.domain.usecase.favourites

import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class FavouritesUseCase(
    private val accountUseCase: AccountUseCase,
    private val mediaUseCase: MediaUseCase,
) {
    suspend fun getFavourites(): List<Media>{
        return accountUseCase.getUserInfo()?.favourites.orEmpty().map { withContext(Dispatcher.IO){ async { mediaUseCase.getPhotoUseCase(it.toInt()) }.await() } }
    }
}