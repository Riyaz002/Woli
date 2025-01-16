package com.wiseowl.woli.data.coroutine

import com.wiseowl.woli.domain.coroutine.Dispatcher
import kotlinx.coroutines.Dispatchers

class Dispatcher: Dispatcher() {
    override val MAIN: Dispatchers
        get() = Dispatchers.Main
    override val IO: Dispatchers
        get() = Dispatchers.IO
    override val DEFAULT: Dispatchers
        get() = Dispatchers.Default
}