package com.wiseowl.woli.ui.screen.common

import androidx.lifecycle.ViewModel
import com.wiseowl.woli.domain.event.Action
import kotlinx.coroutines.flow.MutableStateFlow
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import java.util.Timer
import java.util.TimerTask

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

    private val timers: HashMap<String, Timer> = hashMapOf()

    /**
     * Perform [validation] with some delay.
     * If the function is called with the same [label] while the previous one hasn't been executed, the previous [validation] will be canceled.
     * The [label] is unique identifier for the [validation].
     */
    fun validate(label: String, validation: () -> Unit){
        timers[label]?.cancel()
        val newTimer = Timer()
        timers[label] = newTimer
        newTimer.schedule(
            object : TimerTask() {
                override fun run() {
                    validation()
                }
            }, 1000
        )
    }

    override fun onCleared() {
        super.onCleared()
        timers.clear()
    }
}

