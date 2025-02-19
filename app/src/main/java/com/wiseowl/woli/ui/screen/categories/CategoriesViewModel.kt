package com.wiseowl.woli.ui.screen.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.usecase.categories.CategoriesUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.categories.model.CategoriesModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel(categoriesUseCase: CategoriesUseCase): ViewModel() {
    private val _state = MutableStateFlow<Result<CategoriesModel>>(Result.Loading())
    private val state = MutableStateFlow(Result.Loading<CategoriesModel>())
    init {
        viewModelScope.launch(Dispatcher.IO) {
            _state.update { Result.Success(CategoriesModel(categories = categoriesUseCase.getCategoriesUseCase())) }
        }
    }
}