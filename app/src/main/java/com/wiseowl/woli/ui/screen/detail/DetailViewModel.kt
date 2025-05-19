package com.wiseowl.woli.ui.screen.detail

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt
import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase

class DetailViewModel(
    imageId: String,
    private val useCase: DetailUseCase,
) : PageViewModel<DetailModel>() {
    init {
        viewModelScope.launch {
            val image = viewModelScope.async(Dispatcher.IO) { useCase.mediaUseCase.getPhotoUseCase(imageId.toInt()) }.await()
            val accentColor = image.avgColor?.toColorInt() ?: android.graphics.Color.GRAY
            val complementaryColor = useCase.getComplementaryColorUseCase(accentColor)
            _state.update { s ->
                Result.Success(
                    DetailModel(
                        description = image.alt,
                        categories = listOf(),
                        accentColor = accentColor,
                        complementaryColor = complementaryColor
                    )
                )
            }
            val bitmap = viewModelScope.async {  useCase.getBitmapUseCase(image.src!!.large) }.await()
            if(bitmap==null){
                _state.update { Result.Error(Error("Oops, Error Loading Image!")) }
                return@launch
            }
            _state.update { s ->
                if(s is Result.Success) s.copy(s.data.copy(image = bitmap))
                else Result.Success(DetailModel(image = bitmap))
            }
        }
    }

    override fun onEvent(action: Action) {
        when (action) {
            is DetailAction.OnClickImage -> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(imagePreviewPopupVisible = true))
                    } else state
                }
            }

            is DetailAction.OnClickSetWallpaper -> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(setWallpaperPopupVisible = true))
                    } else state
                }
            }


            is DetailAction.OnDismissImagePreview -> _state.update { state ->
                if (state is Result.Success) {
                    Result.Success(state.data.copy(imagePreviewPopupVisible = false))
                } else state
            }

            is DetailAction.OnClickSetAs -> _state.value.let { state ->
                onEvent(DetailAction.OnDismissSetWallpaperDialog)
                if (state is Result.Success) {
                    viewModelScope.launch(Dispatcher.IO) {
                        ActionHandler.perform(Action.Progress(true))
                        useCase.setWallpaperUseCase(
                            state.data.image!!,
                            action.setWallpaperType
                        )
                        ActionHandler.perform(Action.Progress(false))
                    }
                }
            }

            is DetailAction.OnDismissSetWallpaperDialog -> _state.update { state ->
                if (state is Result.Success) {
                    Result.Success(state.data.copy(setWallpaperPopupVisible = false))
                } else state
            }

            is DetailAction.OnClickSimilarImage -> {
                ActionHandler.perform(Action.Navigate(Screen.DETAIL, mapOf(Screen.DETAIL.ARG_IMAGE_ID to action.imageId.toString())))
            }

            is DetailAction.OnClickCategory -> {
                ActionHandler.perform(Action.Navigate(Screen.CATEGORY, mapOf(Screen.CATEGORY.ARG_CATEGORY to action.category)))
            }
        }
    }
}