package com.wiseowl.woli.ui.screen.collection

import com.wiseowl.woli.ui.event.Action

sealed class CollectionAction: Action {
    class LoadPage(val pageNo: Int): CollectionAction()
}