package com.techcode.palplato.domain.model


data class User(
	val uid: String = "",
	val name: String = "",
	val lastname: String = "",
	val email: String = "",
	val rol: String = "",
	val state: String = "",
	val date: Long = 0L
)