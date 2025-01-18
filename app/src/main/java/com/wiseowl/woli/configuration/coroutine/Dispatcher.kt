package com.wiseowl.woli.configuration.coroutine

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

object Dispatcher {
    val MAIN: CoroutineDispatcher = Dispatchers.Main
    val IO: CoroutineDispatcher = Dispatchers.IO
    val DEFAULT: CoroutineDispatcher = Dispatchers.Default
}