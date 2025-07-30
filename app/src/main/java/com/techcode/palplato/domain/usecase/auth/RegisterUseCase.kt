package com.techcode.palplato.domain.usecase.auth

import com.google.firebase.auth.AuthResult
import com.techcode.palplato.domain.model.User
import com.techcode.palplato.domain.repository.AuthRepository
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.flow.Flow

class RegisterUseCase(
	private val repository: AuthRepository
) {
	suspend operator fun invoke(user: User, password: String): Flow<Resource<AuthResult>> {
		return repository.registerUser(user, password)
	}
}