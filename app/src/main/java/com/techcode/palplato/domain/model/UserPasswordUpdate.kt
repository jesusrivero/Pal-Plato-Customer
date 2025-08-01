package com.techcode.palplato.domain.model

data class UserPasswordUpdate(
	val currentPassword: String,
	val newPassword: String
)
