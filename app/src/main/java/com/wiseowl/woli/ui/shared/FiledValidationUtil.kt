package com.wiseowl.woli.ui.shared

import java.util.Timer
import java.util.TimerTask

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