package com.techcode.palplato.domain.usecase.auth.updateprofiledates

import com.techcode.palplato.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserEmailUseCase @Inject constructor(
	private val userRepository: UserRepository
) {
	suspend operator fun invoke(newEmail: String): Result<Unit> {
		return userRepository.updateUserEmail(newEmail)
	}
}
