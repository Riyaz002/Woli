package com.wiseowl.woli.ui.screen.favourites

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.favourites.model.FavouritesModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val accountUseCase: AccountUseCase,
    private val mediaUseCase: MediaUseCase
): ScreenViewModel<FavouritesModel>() {

    init {
        viewModelScope.launch(Dispatcher.IO){
            accountUseCase.userState.collect{ userState ->
                val favouriteMedias = userState.currentUser?.favourites?.map {
                    async{ mediaUseCase.getPhotoUseCase(it.toInt()) }.await()
                }
                _state.update {
                    Result.Success(FavouritesModel(favouriteMedias.orEmpty()))
                }
            }
        }
    }

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<FavouritesAction.OnClickFavouritesIcon> { action ->
                _state.ifSuccess {
                    it.copy(showRemoveDialog = true, mediaIdForRemoveDialog = action.mediaId)
                }
            }

            on<FavouritesAction.OnClickRemoveFromFavourites> { action ->
                _state.ifSuccess {
                    //DISMISS DIALOG
                    viewModelScope.launch {
                        accountUseCase.removeFromFavourites(action.mediaId)
                    }
                    it.copy(showRemoveDialog = false, mediaIdForRemoveDialog = null)
                }
            }
            on<FavouritesAction.OnClickDismissRemoveRequest> {
                _state.ifSuccess {
                    it.copy(showRemoveDialog = false, mediaIdForRemoveDialog = null)
                }
            }
        }
}