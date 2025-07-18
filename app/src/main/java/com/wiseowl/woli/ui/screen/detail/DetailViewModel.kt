package com.wiseowl.woli.ui.screen.detail

import android.os.Environment
import androidx.core.graphics.toColorInt
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.configuration.coroutine.Dispatcher
import com.wiseowl.woli.domain.repository.media.model.Media
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<DetailAction.OnClickImage> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(imagePreviewPopupVisible = true))
                    } else state
                }
            }

            on<DetailAction.OnClickSetWallpaper> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(setWallpaperPopupVisible = true))
                    } else state
                }
            }


            on<DetailAction.OnDismissImagePreview> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(imagePreviewPopupVisible = false))
                    } else state
                }
            }

            on<DetailAction.OnClickSetAs> { action ->
                _state.value.let { state ->
                    onEvent(DetailAction.OnDismissSetWallpaperDialog)
                    if (state is Result.Success) {
                        viewModelScope.launch(Dispatcher.IO) {
                            ActionHandler.perform(Action.Progress(true))
                            val image =
                                useCase.getBitmapUseCase(state.data.media?.src?.portrait)
                                    ?: return@launch
                            useCase.setWallpaperUseCase(
                                image,
                                action.setWallpaperType
                            )
                            ActionHandler.perform(Action.Progress(false))
                        }
                    }
                }
            }

            on<DetailAction.OnDismissSetWallpaperDialog> {
                _state.update { state ->
                    if (state is Result.Success) {
                        Result.Success(state.data.copy(setWallpaperPopupVisible = false))
                    } else state
                }
            }

            on<DetailAction.OnClickSimilarImage> { action ->
                ActionHandler.perform(
                    Action.Navigate(
                        Screen.DETAIL,
                        mapOf(Screen.DETAIL.ARG_IMAGE_ID to action.imageId.toString())
                    )
                )
            }

            on<DetailAction.OnClickDownload> {
                media.src?.large?.toUri()?.let { fileUri ->
                    val destinationUri =
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                            .toUri()
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