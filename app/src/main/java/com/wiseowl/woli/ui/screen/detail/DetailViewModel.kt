package com.wiseowl.woli.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import com.wiseowl.woli.ui.screen.detail.model.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DetailViewModel(imageId: String, detailUseCase: DetailUseCase): ViewModel() {
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
                //SetWallpaper
            }

            is DetailEvent.OnDismissImagePreview -> {
                _state.update { state ->
                    if (state is DetailState.Success) {
                        DetailState.Success(state.detailModel.copy(imagePreviewPopupVisible = false))
                    } else state
                }
            }
        }
    }
}