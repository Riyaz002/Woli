package com.wiseowl.woli.ui.shared

import java.util.Timer
import java.util.TimerTask

private val timers: HashMap<String, Timer> = hashMapOf()

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