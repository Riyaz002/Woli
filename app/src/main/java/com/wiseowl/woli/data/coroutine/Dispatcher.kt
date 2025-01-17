package com.wiseowl.woli.data.coroutine

import com.wiseowl.woli.domain.coroutine.Dispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Dispatcher: Dispatcher() {
    override val MAIN: CoroutineDispatcher
        get() = Dispatchers.Main
    override val IO: CoroutineDispatcher
        get() = Dispatchers.IO
    override val DEFAULT: CoroutineDispatcher
        get() = Dispatchers.Default
}