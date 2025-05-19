package com.wiseowl.woli.ui.screen.collection

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.domain.repository.CategoryRepository
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.collection.model.CollectionModel
import kotlinx.coroutines.launch

class CollectionViewModel(category: String, repository: CategoryRepository): ScreenViewModel<CollectionModel>() {

    init {
        viewModelScope.launch(Dispatcher.IO) {
            val images = repository.getCategoryImages(category)
            _state.value = Result.Success(CollectionModel(category = category, images = images))
        }
    }

    override fun onEvent(action: Action){
        ActionHandler.perform(action)
    }
}