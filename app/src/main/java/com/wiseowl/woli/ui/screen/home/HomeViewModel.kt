package com.wiseowl.woli.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.home.model.HomePageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase): ViewModel() {
    private val _state = MutableStateFlow<Result<HomePageModel>>(Result.Loading())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val totalPages = homeUseCase.getPageUseCase.getTotalPageCount()
            val page = homeUseCase.getPageUseCase.getPage(totalPages)
            val homePageModel = HomePageModel(page.images, currentPage = totalPages)
            _state.update { _ -> Result.Success(homePageModel) }
        }
    }

    fun onEvent(event: Action){
        when(event){
            is HomeEvent.OnClickImage -> {
                ActionHandler.perform(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to event.imageId.toString())))
            }
            is HomeEvent.LoadNextPage -> loadNextPage(event.page)
        }
    }

    private fun loadNextPage(pageNo: Int) {
        viewModelScope.launch {
            val page = homeUseCase.getPageUseCase.getPage(pageNo)
            val currentState = _state.value
            if(currentState is Result.Success){
                currentState.data
                val homePageModel: HomePageModel = currentState.data.copy(
                    images = currentState.data.images?.plus(page.images ?: listOf()),
                    currentPage = pageNo
                )
                _state.update { Result.Success(homePageModel) }
            }
        }
    }
}