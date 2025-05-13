package com.wiseowl.woli.ui.screen.categories

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.categories.model.CollectionModel
import com.wiseowl.woli.ui.screen.common.PageViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(private val mediaUseCase: MediaUseCase): PageViewModel<CollectionModel>() {
    init {
        viewModelScope.launch(Dispatcher.IO) {
            val currentPage = 0
            val data = mediaUseCase.getCollectionPageUseCase(currentPage)
            _state.update {
                Result.Success(
                    CollectionModel(
                        categories = data.collections,
                        currentPage,
                        !data.nextPage.isNullOrEmpty()
                    )
                )
            }
            mediaUseCase.getCollectionPageUseCase
        }
    }

    override fun onEvent(action: Action){
        when(action){
            is CategoriesEvent.LoadPage -> {
                viewModelScope.launch(Dispatcher.IO) {
                    _state.update { state ->
                        (state as Result.Success<CollectionModel>).let {
                            val categories = it.data.categories as MutableList
                            val pageData = mediaUseCase.getCollectionPageUseCase(action.pageNo).collections
                            categories.addAll(pageData)
                            Result.Success(
                                it.data.copy(
                                    categories = categories,
                                    currentPage = action.pageNo,
                                    hasNext = pageData.isNotEmpty()
                                )
                            )
                        }
                    }
                }
            }
            else -> ActionHandler.perform(action)
        }
    }
}