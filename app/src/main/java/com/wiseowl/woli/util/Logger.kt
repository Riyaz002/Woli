package com.wiseowl.woli.util

import android.util.Log

const val TAG = "WOLI"

object Logger {
    fun Logger.e(message: String){
        Log.e(TAG, message)
    }

    fun Logger.d(message: String){
        Log.d(TAG, message)
    }

    fun Logger.v(message: String){
        Log.v(TAG, message)
    }

    fun Logger.w(message: String){
        Log.w(TAG, message)
    }

    fun <T> tryWithLog(operation: () -> T?): T? {
        return try {
            operation()
        } catch (e: Exception){
            e(e.toString())
            null
        }
    }
}