package com.wiseowl.woli.ui.screen.category

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.repository.CategoryRepository
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.category.model.CategoryModel
import kotlinx.coroutines.launch

class CategoryViewModel(category: String, repository: CategoryRepository): PageViewModel<CategoryModel>() {

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val images = repository.getCategoryImages(category)
            _state.value = Result.Success(CategoryModel(category = category, images = images))
        }
    }

    override fun onEvent(action: Action){
        ActionHandler.perform(action)
    }
}