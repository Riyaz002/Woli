package com.wiseowl.woli.ui.screen.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.ui.event.Action
import kotlinx.coroutines.flow.MutableStateFlow
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.ui.event.Reducer
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.event.reducerOf
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask

/**
 * Base class for all ViewModels in the app.
 * Complimenting MVI pattern by have a common method that handles [Action].
 */
abstract class ScreenViewModel<T>(
    initialValue: Result<T> = Result.Loading()
): ViewModel() {

    constructor(op: suspend () -> T): this(Result.Loading()){
        viewModelScope.launch {
            _state.update { Result.Success(op()) }
        }
    }

    //State hoisting for the page
    protected val _state = MutableStateFlow(initialValue)
    val state: StateFlow<Result<T>> = _state

    abstract val actionReducer: ReducerBuilder.() -> Unit
    private val reducer: Reducer = reducerOf { actionReducer() }

    fun onEvent(action: Action){
        val isHandled = reducer.handle(action)
        if(!isHandled) ActionHandler.perform(action)
    }

    fun MutableStateFlow<Result<T>>.ifSuccess(block: (T) -> T) {
        if (this.value is Result.Success) {
            update { Result.Success(block((this.value as Result.Success<T>).data))  }
        }
    }

    private val timers: HashMap<String, Timer> = hashMapOf()

    /**
     * Perform [action] with some delay.
     * The [label] is unique identifier for the [action].
     * If the function is called with the same [label] while the previous one hasn't been executed, the previous [action] will be canceled.
     */
    fun validate(label: String, action: () -> Unit){
        timers[label]?.cancel()
        val newTimer = Timer()
        timers[label] = newTimer
        newTimer.schedule(
            object : TimerTask() {
                override fun run() {
                    action()
                }
            }, 1000
        )
    }

    override fun onCleared() {
        super.onCleared()
        timers.clear()
    }
}

