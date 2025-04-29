package com.wiseowl.woli.util

import android.util.Log

const val TAG = "WOLI"

object Logger {
    fun Logger.e(message: String){
        Log.e(TAG, this.toString())
    }

    fun Logger.d(message: String){
        Log.d(TAG, this.toString())
    }

    fun Logger.v(message: String){
        Log.v(TAG, this.toString())
    }

    fun Logger.w(message: String){
        Log.w(TAG, this.toString())
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