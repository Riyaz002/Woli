package com.wiseowl.woli.ui.screen.category

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.repository.CategoryRepository
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.category.model.CategoryModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(category: String, repository: CategoryRepository): ViewModel() {
    private val _state = MutableStateFlow<Result<CategoryModel>>(Result.Loading())
    val state: StateFlow<Result<CategoryModel>> get() = _state

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val images = repository.getCategoryImages(category)
            _state.value = Result.Success(CategoryModel(category = category, images = images))
        }
    }

    fun onEvent(action: Action) = Unit
}