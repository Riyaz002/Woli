package com.wiseowl.woli.domain.coroutine

import kotlinx.coroutines.CoroutineDispatcher

abstract class Dispatcher {
    abstract val MAIN: CoroutineDispatcher
    abstract val IO: CoroutineDispatcher
    abstract val DEFAULT: CoroutineDispatcher
}