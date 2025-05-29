package com.wiseowl.woli.ui.screen.collections

import com.wiseowl.woli.ui.event.Action

sealed class CollectionsAction: Action {
    data class LoadPage(val pageNo: Int): CollectionsAction()
    data class OnClickMedia(val id: Long): CollectionsAction()
}