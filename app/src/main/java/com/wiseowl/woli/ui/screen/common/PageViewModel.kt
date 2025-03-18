package com.wiseowl.woli.ui.screen.common

import androidx.lifecycle.ViewModel
import com.wiseowl.woli.domain.event.Action
import kotlinx.coroutines.flow.MutableStateFlow
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.flow.StateFlow

/**
 * Base class for all ViewModels in the app.
 * Complimenting MVI pattern by have a common method that handles [Action].
 */
abstract class PageViewModel<T>: ViewModel() {
    //State hoisting for the page
    protected val _state = MutableStateFlow<Result<T>>(Result.Loading())
    val state: StateFlow<Result<T>> = _state

    abstract fun onEvent(action: Action)
}