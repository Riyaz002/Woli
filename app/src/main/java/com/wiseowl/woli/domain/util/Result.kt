package com.wiseowl.woli.domain.util


/**
 * Generic result class that represent different state of the page
 * Use it as a wrapper for the data model used to draw the page
 * [T] is the type of the data model
 */
sealed class Result<T> {
    class Loading<T>: Result<T>()
    data class Success<T>(val data: T): Result<T>()
    data class Error<T>(val error: com.wiseowl.woli.domain.model.Error): Result<T>()
}