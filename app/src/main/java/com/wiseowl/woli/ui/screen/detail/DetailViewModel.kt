package com.wiseowl.woli.ui.screen.detail

import kotlin.collections.flatten
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import com.wiseowl.woli.ui.screen.detail.model.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(imageId: String, private val detailUseCase: DetailUseCase) : ViewModel() {
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading).also { s ->
        viewModelScope.launch {
            ActionHandler.perform(Action.Progress(true))
            val image = detailUseCase.getImageUseCase(imageId.toInt())
            val bitmap = detailUseCase.getBitmapUseCase(imageId.toInt())
            val color = detailUseCase.getColorUseCase(bitmap!!)
            val similarImages = (image?.categories?.map { detailUseCase.getImagesForCategoryUseCase(it) } as List<List<Image>>).flatten().distinctBy { it.id }.filter { it.id != image.id }
            s.emit(
                DetailState.Success(
                    DetailModel(
                        image = bitmap,
                        description = image.description,
                        categories = image.categories ?: listOf(),
                        accentColor = color.primary,
                        complementaryColor = color.secondary,
                        similarImages = similarImages
                    )
                )
            )
            ActionHandler.perform(Action.Progress(false))
        }
    }
    val state = _state.asStateFlow()

    fun onEvent(event: Action) {
        when (event) {
            is DetailEvent.OnClickImage -> {
                _state.update { state ->
                    if (state is DetailState.Success) {
                        DetailState.Success(state.detailModel.copy(imagePreviewPopupVisible = true))
                    } else state
                }
            }

            is DetailEvent.OnClickSetWallpaper -> {
                _state.update { state ->
                    if (state is DetailState.Success) {
                        DetailState.Success(state.detailModel.copy(setWallpaperPopupVisible = true))
                    } else state
                }
            }


            is DetailEvent.OnDismissImagePreview -> _state.update { state ->
                if (state is DetailState.Success) {
                    DetailState.Success(state.detailModel.copy(imagePreviewPopupVisible = false))
                } else state
            }

            is DetailEvent.OnClickSetAs -> _state.value.let { state ->
                onEvent(DetailEvent.OnDismissSetWallpaperDialog)
                if (state is DetailState.Success) {
                    viewModelScope.launch(Dispatcher.IO) {
                        ActionHandler.perform(Action.Progress(true))
                        detailUseCase.setWallpaperUseCase(
                            state.detailModel.image!!,
                            event.setWallpaperType
                        )
                        ActionHandler.perform(Action.Progress(false))
                    }
                }
            }

            is DetailEvent.OnDismissSetWallpaperDialog -> _state.update { state ->
                if (state is DetailState.Success) {
                    DetailState.Success(state.detailModel.copy(setWallpaperPopupVisible = false))
                } else state
            }

            is DetailEvent.OnClickSimilarImage -> {
                ActionHandler.perform(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to event.imageId.toString())))
            }
        }
    }
}