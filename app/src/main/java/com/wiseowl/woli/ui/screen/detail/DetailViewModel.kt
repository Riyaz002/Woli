package com.wiseowl.woli.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.event.EventHandler
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperType
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import com.wiseowl.woli.ui.screen.detail.model.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(imageId: String, private val detailUseCase: DetailUseCase): ViewModel() {
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading).also {
        viewModelScope.launch {
            val image = detailUseCase.getBitmapUseCase(imageId.toInt())
            it.emit(DetailState.Success(DetailModel(image)))
        }
    }
    val state = _state.asStateFlow()

    fun onEvent(event: Event){
        when(event){
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

            is DetailEvent.OnClickSetAsHomeScreen -> _state.value.let { state ->
                if (state is DetailState.Success) {
                    EventHandler.sendEvent(Event.Progress(true))
                    detailUseCase.setWallpaperUseCase(
                        state.detailModel.image!!,
                        SetWallpaperType.ONLY_HOME
                    )
                    EventHandler.sendEvent(Event.Progress(false))
                }
            }


            is DetailEvent.OnClickSetAsLockScreen -> _state.value.let { state ->
                if (state is DetailState.Success) {
                    EventHandler.sendEvent(Event.Progress(true))
                    detailUseCase.setWallpaperUseCase(
                        state.detailModel.image!!,
                        SetWallpaperType.ONLY_LOCK
                    )
                    EventHandler.sendEvent(Event.Progress(false))
                }
            }


            is DetailEvent.OnClickSetAsBoth -> _state.value.let { state ->
                if (state is DetailState.Success) {
                    EventHandler.sendEvent(Event.Progress(true))
                    detailUseCase.setWallpaperUseCase(
                        state.detailModel.image!!,
                        SetWallpaperType.FOR_BOTH
                    )
                    EventHandler.sendEvent(Event.Progress(false))
                }
            }


            is DetailEvent.OnClickUseOtherApp -> _state.value.let { state ->
                if (state is DetailState.Success) {
                    EventHandler.sendEvent(Event.Progress(true))
                    detailUseCase.setWallpaperUseCase(
                        state.detailModel.image!!,
                        SetWallpaperType.USE_OTHER_APP
                    )
                    EventHandler.sendEvent(Event.Progress(false))
                }
            }

            is DetailEvent.OnDismissSetWallpaperDialog -> _state.update { state ->
                if (state is DetailState.Success) {
                    DetailState.Success(state.detailModel.copy(setWallpaperPopupVisible = false))
                } else state
            }
        }
    }
}