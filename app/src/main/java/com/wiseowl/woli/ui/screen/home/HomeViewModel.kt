package com.wiseowl.woli.ui.screen.home

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.home.model.HomePageModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase): PageViewModel<HomePageModel>() {

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val totalPages = homeUseCase.getPageUseCase.getTotalPageCount()
            val page = homeUseCase.getPageUseCase.getPage(totalPages)
            val homePageModel = HomePageModel(page.data, currentPage = totalPages)
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
            val page = homeUseCase.getPageUseCase.getPage(pageNo)
            val currentState = _state.value
            if(currentState is Result.Success){
                currentState.data
                val homePageModel: HomePageModel = currentState.data.copy(
                    images = currentState.data.images?.plus(page.data ?: listOf()),
                    currentPage = pageNo
                )
                _state.update { Result.Success(homePageModel) }
            }
        }
    }
}