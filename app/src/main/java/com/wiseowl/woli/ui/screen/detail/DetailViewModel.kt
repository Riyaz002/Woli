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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(imageId: String, private val detailUseCase: DetailUseCase) : ViewModel() {
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading).also { s ->
        viewModelScope.launch {
            ActionHandler.perform(Action.Progress(true))
            try {
                // Initiate asynchronous API calls
                val imageDeferred = async { detailUseCase.getImageUseCase(imageId.toInt()) }
                val bitmapDeferred = async { detailUseCase.getBitmapUseCase(imageId.toInt()) }

                // Await results
                val image = imageDeferred.await()
                val bitmap = bitmapDeferred.await()

                // Ensure bitmap is not null before proceeding
                if (bitmap != null) {
                    val colorDeferred = async { detailUseCase.getColorUseCase(bitmap) }

                    // Fetch similar images concurrently for each category
                    val similarImagesDeferred = image?.categories?.map { category ->
                        async { detailUseCase.getImagesForCategoryUseCase(category) }
                    } ?: emptyList()

                    // Await color and similar images results
                    val color = colorDeferred.await()
                    val similarImages = (similarImagesDeferred.awaitAll() as List<List<Image>>)
                        .flatten()
                        .distinctBy { it.id }
                        .filter { it.id != image?.id }

                    // Emit success state with the combined data
                    s.emit(
                        DetailState.Success(
                            DetailModel(
                                image = bitmap,
                                description = image?.description.orEmpty(),
                                categories = image?.categories.orEmpty(),
                                accentColor = color.primary,
                                complementaryColor = color.secondary,
                                similarImages = similarImages
                            )
                        )
                    )
                } else {
                    // Handle the case where bitmap is null
                    s.emit(DetailState.Error("Bitmap is null"))
                }
            } catch (e: Exception) {
                // Handle exceptions and emit error state
                s.emit(DetailState.Error(e.message ?: "An error occurred"))
            } finally {
                ActionHandler.perform(Action.Progress(false))
            }
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