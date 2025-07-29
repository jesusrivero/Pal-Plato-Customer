package com.techcode.palplato.domain.usecase

import com.google.firebase.auth.AuthResult
import com.techcode.palplato.domain.repository.AuthRepository
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.flow.Flow

class LoginUseCase (
	private val repository: AuthRepository
) {
	suspend operator fun invoke(email:String, password:String) : Flow<Resource<AuthResult>> {
		return repository.loginUser(email, password)
	}
}