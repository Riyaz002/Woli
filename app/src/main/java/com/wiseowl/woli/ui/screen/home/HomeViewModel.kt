package com.wiseowl.woli.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventHandler
import com.wiseowl.woli.ui.screen.home.model.HomeState
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.home.model.HomePageModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(private val homeUseCase: HomeUseCase): ViewModel() {
    private val _state = MutableStateFlow<HomeState>(HomeState.Loading)
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val page = homeUseCase.getPageUseCase(1)
            val homePageModel = HomePageModel(page.images, allPageLoaded = page.images.isNullOrEmpty())
            _state.update { _ -> HomeState.Success(homePageModel) }
        }
    }

    fun onEvent(event: Event){
        when(event){
            is HomeEvent.OnClickImage -> {
                EventHandler.sendEvent(Event.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to event.imageId.toString())))
            }
            is HomeEvent.LoadNextPage -> loadNextPage(event.page)
        }
    }

    private fun loadNextPage(pageNo: Int) {
        viewModelScope.launch {
            val page = homeUseCase.getPageUseCase(pageNo)
            val currentState = _state.value
            if(currentState is HomeState.Success){
                val homePageModel: HomePageModel = currentState.homePageModel.copy(
                    images = currentState.homePageModel.images?.plus(page.images ?: listOf()),
                    allPageLoaded = page.images.isNullOrEmpty(),
                    currentPage = pageNo
                )
                _state.update { HomeState.Success(homePageModel) }
            }
        }
    }
}