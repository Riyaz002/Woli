package com.wiseowl.woli.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.home.model.HomePageModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: MediaUseCase): PageViewModel<HomePageModel>() {

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val page = homeUseCase.getPageUseCase(0)
            val homePageModel = HomePageModel(page.photos, 0)
            _state.update { _ -> Result.Success(homePageModel) }
        }
    }

    override fun onEvent(action: Action){
        when(action){
            is HomeEvent.OnClickImage -> {
                ActionHandler.perform(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to action.imageId.toString())))
            }
            is HomeEvent.LoadNextPage -> loadNextPage(action.page)
        }
    }

    private fun loadNextPage(pageNo: Int) {
        viewModelScope.launch {
            val page = homeUseCase.getPageUseCase(pageNo)
            val currentState = _state.value
            if(currentState is Result.Success){
                val photos = currentState.data.images.toMutableList()
                photos.addAll(page.photos)
                val homePageModel: HomePageModel = currentState.data.copy(
                    images = photos,
                    currentPage = page.page
                )
                _state.update { Result.Success(homePageModel) }
            }
        }
    }
}