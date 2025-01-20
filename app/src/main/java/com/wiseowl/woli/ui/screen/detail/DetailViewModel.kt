package com.wiseowl.woli.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.usecase.detail.DetailUseCase
import com.wiseowl.woli.ui.screen.detail.model.DetailModel
import com.wiseowl.woli.ui.screen.detail.model.DetailState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(imageId: String, detailUseCase: DetailUseCase): ViewModel() {
    private val _state = MutableStateFlow<DetailState>(DetailState.Loading).also {
        viewModelScope.launch {
            val image = detailUseCase.getImageUseCase(imageId.toInt())
            it.emit(DetailState.Success(DetailModel(image)))
        }
    }
    val state = _state.asStateFlow()

    fun onEvent(event: Event){
        when(event){

        }
    }
}