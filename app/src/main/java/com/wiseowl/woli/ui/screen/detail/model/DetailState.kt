package com.wiseowl.woli.ui.screen.detail.model

sealed class DetailState {
    object Loading: DetailState()
    data class Success(val detailModel: DetailModel): DetailState()
    data class Error(val message: String): DetailState()
}