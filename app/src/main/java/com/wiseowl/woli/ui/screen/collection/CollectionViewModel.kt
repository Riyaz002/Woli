package com.wiseowl.woli.ui.screen.collection

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.usecase.common.media.MediaUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.screen.collection.model.CollectionModel
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CollectionViewModel(private val categoryId: String, categoryTitle: String, private val mediaUseCase: MediaUseCase): ScreenViewModel<CollectionModel>() {

    init {
        viewModelScope.launch {
            val initialPage = 0
            val medias = mediaUseCase.getCollectionPageUseCase(categoryId, initialPage)
            _state.value = Result.Success(CollectionModel(category = categoryTitle, media = medias, initialPage, hasNext = medias.isNotEmpty()))
        }
    }

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<CollectionAction.LoadPage> { action ->
                viewModelScope.launch {
                    _state.update { state ->
                        (state as Result.Success<CollectionModel>).let {
                            val medias = it.data.media as MutableList
                            val newMedias = mediaUseCase.getCollectionPageUseCase(categoryId, action.pageNo)
                            medias.addAll(newMedias)
                            Result.Success(
                                it.data.copy(
                                    currentPage = action.pageNo,
                                    media = medias,
                                    hasNext = newMedias.isNotEmpty()
                                )
                            )
                        }
                    }
                }
            }
        }
}