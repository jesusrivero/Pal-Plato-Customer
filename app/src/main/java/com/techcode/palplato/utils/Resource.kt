package com.techcode.palplato.utils

sealed class Resource<out T> {
	object Idle : Resource<Nothing>()
	class Loading<out T> : Resource<T>()
	data class Success<out T>(val result: T) : Resource<T>()
	data class Error<out T>(val message: String) : Resource<T>()
}