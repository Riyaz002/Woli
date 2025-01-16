package com.wiseowl.woli.domain.coroutine

import kotlinx.coroutines.Dispatchers

abstract class Dispatcher {
    abstract val MAIN: Dispatchers
    abstract val IO: Dispatchers
    abstract val DEFAULT: Dispatchers
}