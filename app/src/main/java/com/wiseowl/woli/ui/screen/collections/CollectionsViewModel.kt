package com.wiseowl.woli.ui.screen.collections

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.domain.repository.media.model.Collection
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.collections.model.CollectionModel
import com.wiseowl.woli.ui.screen.common.PageViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionsViewModel(private val mediaUseCase: MediaUseCase): PageViewModel<CollectionModel>() {
    init {
        viewModelScope.launch(Dispatcher.IO) {
            val currentPage = 0
            val data = mediaUseCase.getCollectionPageUseCase(currentPage)
            val state = Result.Success(
                CollectionModel(
                    categories = data.collections,
                    currentPage,
                    !data.nextPage.isNullOrEmpty()
                )
            )
            _state.update { state }
            val contentFullCollection = data.collections.map {
                val medias = mediaUseCase.getCollectionUseCase(it.id)
                Collection(
                    id = it.id,
                    title = it.title,
                    images = medias,
                    description = it.description,
                    mediaCount = it.mediaCount,
                    photosCount = it.photosCount,
                    isPrivate = it.isPrivate,
                    videosCount = it.videosCount
                )
            }

            _state.update{ state.copy(data = state.data.copy(categories = contentFullCollection)) }

            mediaUseCase.getCollectionPageUseCase
        }
    }

    override fun onEvent(action: Action){
        when(action){
            is CollectionsAction.LoadPage -> {
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
            is CollectionsAction.OnClickMedia -> {
                ActionHandler.perform(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to action.id.toString())))
            }
            else -> ActionHandler.perform(action)
        }
    }
}