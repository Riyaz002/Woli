package com.wiseowl.woli.ui.screen.detail

import kotlin.collections.flatten
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.PageViewModel
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import com.wiseowl.woli.ui.screen.detail.model.SimilarImageModel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(imageId: String, private val detailUseCase: DetailUseCase) : PageViewModel<DetailModel>() {
    init {
        viewModelScope.launch {
            val image = viewModelScope.async(Dispatcher.IO) { detailUseCase.getImageUseCase(imageId.toInt()) }.await()
            _state.update { s ->
                if(s is Result.Success) s.copy(
                    s.data.copy(
                        description = image?.description,
                        categories = image?.categories ?: listOf(),
                        accentColor = image?.color?.primary,
                        complementaryColor = image?.color?.secondary
                    )
                ) else Result.Success(DetailModel(
                    description = image?.description,
                    categories = image?.categories ?: listOf(),
                    accentColor = image?.color?.primary,
                    complementaryColor = image?.color?.secondary
                ))
            }
            val bitmap = viewModelScope.async {  detailUseCase.getBitmapUseCase(image?.url!!) }.await()
            if(bitmap==null){
                _state.update { Result.Error(Error("Oops, Error Loading Image!")) }
                return@launch
            }
            _state.update { s ->
                if(s is Result.Success) s.copy(s.data.copy(image = bitmap))
                else Result.Success(DetailModel(image = bitmap))
            }
            val similarImagesDeferred = image?.categories?.map { category ->
                async { detailUseCase.getImagesForCategoryUseCase(category) }
            } ?: emptyList()
            val similarImages = (similarImagesDeferred.awaitAll() as List<List<Image>>).flatten().filter { it.id!=image?.id }
            _state.update { s ->
                if(s is Result.Success) s.copy(s.data.copy(similarImage =SimilarImageModel(images = similarImages, false)))
                else Result.Success(DetailModel(image = bitmap))
            }
        }
    }

    override fun onEvent(action: Action) {
        when (action) {
            is DetailEvent.OnClickImage -> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(imagePreviewPopupVisible = true))
                    } else state
                }
            }

            is DetailEvent.OnClickSetWallpaper -> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(setWallpaperPopupVisible = true))
                    } else state
                }
            }


            is DetailEvent.OnDismissImagePreview -> _state.update { state ->
                if (state is Result.Success) {
                    Result.Success(state.data.copy(imagePreviewPopupVisible = false))
                } else state
            }

            is DetailEvent.OnClickSetAs -> _state.value.let { state ->
                onEvent(DetailEvent.OnDismissSetWallpaperDialog)
                if (state is Result.Success) {
                    viewModelScope.launch(Dispatcher.IO) {
                        ActionHandler.perform(Action.Progress(true))
                        detailUseCase.setWallpaperUseCase(
                            state.data.image!!,
                            action.setWallpaperType
                        )
                        ActionHandler.perform(Action.Progress(false))
                    }
                }
            }

            is DetailEvent.OnDismissSetWallpaperDialog -> _state.update { state ->
                if (state is Result.Success) {
                    Result.Success(state.data.copy(setWallpaperPopupVisible = false))
                } else state
            }

            is DetailEvent.OnClickSimilarImage -> {
                ActionHandler.perform(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to action.imageId.toString())))
            }

            is DetailEvent.OnClickCategory -> {
                ActionHandler.perform(Action.Navigate(Screen.CATEGORY, mapOf(Screen.CATEGORY.ARG_CATEGORY to action.category)))
            }
        }
    }
}