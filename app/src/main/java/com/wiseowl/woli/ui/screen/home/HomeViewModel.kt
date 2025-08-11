package com.wiseowl.woli.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.home.model.HomePageModel
import com.wiseowl.woli.ui.shared.launchWithProgress
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val homeUseCase: MediaUseCase,
    private val accountUseCase: AccountUseCase
): ScreenViewModel<HomePageModel>() {

    init {
        viewModelScope.launch {
            val page = homeUseCase.getPhotoPageUseCase(0)
            val homePageModel = HomePageModel(images = page.media, currentPage = 0)
            _state.update { _ -> Result.Success(homePageModel) }
        }
    }

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<HomeAction.OnClickImage> { action ->
                ActionHandler.perform(
                    Action.Navigate(
                        Screen.DETAIL,
                        mapOf(Screen.DETAIL.ARG_IMAGE_ID to action.imageId.toString())
                    )
                )
            }
            on<HomeAction.LoadNextPage>{ loadNextPage() }
            on<HomeAction.OnSearchChange>{ action ->
                _state.ifSuccess{
                    it.copy(search = it.search.copy(value = action.query))
                }
            }
            on<HomeAction.OnClickSearch>{ search() }
            on<HomeAction.OnClickAddToFavourite>{ action ->
                viewModelScope.launch {
                    accountUseCase.addToFavourites(action.mediaId)
                }
                _state.ifSuccess{
                    it.copy(favourites = it.favourites+action.mediaId)
                }
            }
        }

    private fun loadNextPage() {
        viewModelScope.launch {
            val pageNumber = (state.value as Result.Success).data.currentPage.plus(1)
            val page = homeUseCase.getPhotoPageUseCase(pageNumber)
            val currentState = _state.value
            if(currentState is Result.Success){
                val photos = currentState.data.images.toMutableList()
                photos.addAll(page.media)
                val newPhotos = photos.distinctBy { it.id }
                val homePageModel: HomePageModel = currentState.data.copy(
                    images = newPhotos,
                    currentPage = page.page
                )
                _state.update { Result.Success(homePageModel) }
            }
        }
    }

    private fun search() {
        viewModelScope.launchWithProgress {
            val query = (state.value as Result.Success).data.search.value
            if(query.isEmpty()) return@launchWithProgress
            val page = homeUseCase.geSearchUseCase(query, 0)
            val currentState = _state.value
            if(currentState is Result.Success){
                val homePageModel: HomePageModel = currentState.data.copy(
                    images = page.media,
                    currentPage = page.page
                )
                _state.update { Result.Success(homePageModel) }
            }
        }
    }
}