package com.wiseowl.woli.ui.screen.common

import androidx.lifecycle.ViewModel
import com.wiseowl.woli.domain.event.Action
import kotlinx.coroutines.flow.MutableStateFlow
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

/**
 * Base class for all ViewModels in the app.
 * Complimenting MVI pattern by have a common method that handles [Action].
 */
abstract class PageViewModel<T>(
    initialValue: Result<T> = Result.Loading()
): ViewModel() {
    //State hoisting for the page
    protected val _state = MutableStateFlow(initialValue)
    val state: StateFlow<Result<T>> = _state

    abstract fun onEvent(action: Action)

    fun MutableStateFlow<Result<T>>.ifSuccess(block: (T) -> T) {
        if (this.value is Result.Success) {
            update { Result.Success(block((this.value as Result.Success<T>).data))  }
        }
    }
}

