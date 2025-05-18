package com.wiseowl.woli.ui.screen.collections

import com.wiseowl.woli.domain.event.Action

sealed class CollectionsEvent: Action {
    data class LoadPage(val pageNo: Int): CollectionsEvent()
    data class OnClickMedia(val id: Long): CollectionsEvent()
}