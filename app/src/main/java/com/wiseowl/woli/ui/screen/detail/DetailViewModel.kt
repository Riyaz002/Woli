package com.wiseowl.woli.ui.screen.detail

import android.os.Environment
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase

class DetailViewModel(
    imageId: String,
    private val useCase: DetailUseCase,
) : ScreenViewModel<DetailModel>() {
    lateinit var media: Media
    init {
        viewModelScope.launch {
            media = viewModelScope.async(Dispatcher.IO) { useCase.mediaUseCase.getPhotoUseCase(imageId.toInt()) }.await()
            val accentColor = media.avgColor?.toColorInt() ?: android.graphics.Color.GRAY
            val complementaryColor = useCase.getComplementaryColorUseCase(accentColor)
            _state.update { s ->
                Result.Success(
                    DetailModel(
                        media = media,
                        description = media.alt,
                        categories = listOf(),
                        accentColor = accentColor,
                        complementaryColor = complementaryColor
                    )
                )
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
                        val image = useCase.getBitmapUseCase(state.data.media?.src?.portrait) ?: return@launch
                        useCase.setWallpaperUseCase(
                            image,
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

            is DetailAction.OnClickDownload -> {
                media.src?.large?.toUri()?.let { fileUri ->
                    val destinationUri = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toUri()
                    useCase.saveFileUseCase(
                        fileUri,
                        destinationUri,
                        "Downloading Image",
                        "Image is being downloaded"
                    )
                }
            }
        }
    }
}