package com.wiseowl.woli.ui.screen.categories

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.categories.CategoriesUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.categories.model.CategoriesModel
import com.wiseowl.woli.ui.screen.common.PageViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(private val categoriesUseCase: CategoriesUseCase): PageViewModel<CategoriesModel>() {
    init {
        viewModelScope.launch(Dispatcher.IO) {
            val currentPage = 1
            val data = categoriesUseCase.getCategoriesUseCase.getPage(currentPage).data
            _state.update { Result.Success(CategoriesModel(categories = data, currentPage, !data.isNullOrEmpty())) }
        }
    }

    override fun onEvent(action: Action){
        when(action){
            is CategoriesEvent.LoadPage -> {
                viewModelScope.launch(Dispatcher.IO) {
                    _state.update { state ->
                        (state as Result.Success<CategoriesModel>).let {
                            val categories = it.data.categories as MutableList
                            val pageData = categoriesUseCase.getCategoriesUseCase.getPage(action.pageNo).data.orEmpty()
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