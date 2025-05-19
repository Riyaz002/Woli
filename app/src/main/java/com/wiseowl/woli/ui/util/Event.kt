package com.wiseowl.woli.ui.util

class Event<T>(
    private val data: T
){
    private var isHandled: Boolean = false
    fun getContentIfNotHandled(): T? {
        return if(isHandled){
            null
        } else{
            isHandled = true
            data
        }
    }
}