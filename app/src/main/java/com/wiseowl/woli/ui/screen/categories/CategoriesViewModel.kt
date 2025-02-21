package com.wiseowl.woli.ui.screen.categories

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.categories.CategoriesUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.categories.model.CategoriesModel
import com.wiseowl.woli.ui.screen.common.PageViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(categoriesUseCase: CategoriesUseCase): PageViewModel<CategoriesModel>() {
    init {
        viewModelScope.launch(Dispatcher.IO) {
            _state.update { Result.Success(CategoriesModel(categories = categoriesUseCase.getCategoriesUseCase())) }
        }
    }

    override fun onEvent(action: Action) = Unit
}