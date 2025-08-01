package com.techcode.palplato.domain.model

data class UpdateEmailRequest(
	val newEmail: String,
	val password: String
)