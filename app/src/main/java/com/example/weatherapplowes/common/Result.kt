package com.example.weatherapplowes.common

sealed class Result<out R> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
    data class Message(val message: String): Result<Nothing>()
}
