package com.gsm.presentation.util

sealed class DataState<out T> {
    data class Success<T>(val data:T) : DataState<T>()
    data class Failure(val code: Int, val message: String) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}