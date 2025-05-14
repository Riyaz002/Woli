package com.wiseowl.woli.ui.screen.categories

import com.wiseowl.woli.domain.event.Action

sealed class CategoriesEvent: Action {
    data class LoadPage(val pageNo: Int): CategoriesEvent()
    data class OnClickMedia(val id: Long): CategoriesEvent()
}