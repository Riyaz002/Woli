package com.wiseowl.woli.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.ui.screen.home.model.HomeState
import com.wiseowl.woli.domain.usecase.home.HomeUseCase
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

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.OnClickImage -> {
                //Navigate to detail page
            }
        }
    }

    private fun loadNextPage() {
        val currentState = _state.value
        if(currentState is HomeState.Success){
            if(!currentState.homePageModel.images.isNullOrEmpty()){
                viewModelScope.launch(Dispatcher.IO) {

                }
            }
        }
    }
}