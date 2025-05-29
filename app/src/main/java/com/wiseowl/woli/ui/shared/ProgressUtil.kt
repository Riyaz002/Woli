package com.wiseowl.woli.ui.shared

import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

inline fun runWithProgress(action: () -> Any?): Any?{
    ActionHandler.perform(Action.Progress(true))
    val result = runCatching { action() }
    ActionHandler.perform(Action.Progress(false))
    return result.getOrNull()
}

inline fun CoroutineScope.launchWithProgress(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    crossinline action: suspend CoroutineScope.() -> Unit
): Job {
    return launch(context, start){
        ActionHandler.perform(Action.Progress(true))
        runCatching { action() }
        ActionHandler.perform(Action.Progress(false))
    }
}

