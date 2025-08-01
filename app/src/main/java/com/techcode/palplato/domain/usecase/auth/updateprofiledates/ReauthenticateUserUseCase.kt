package com.techcode.palplato.domain.usecase.auth.updateprofiledates

import com.techcode.palplato.domain.repository.UserRepository
import javax.inject.Inject

class ReauthenticateUserUseCase @Inject constructor(
	private val repository: UserRepository
) {
	suspend operator fun invoke(password: String): Result<Unit> {
		return repository.reauthenticateUser(password)
	}
}
