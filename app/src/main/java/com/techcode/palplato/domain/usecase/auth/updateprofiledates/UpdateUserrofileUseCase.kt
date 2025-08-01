package com.techcode.palplato.domain.usecase.auth.updateprofiledates

import com.techcode.palplato.domain.model.UserProfileUpdate
import com.techcode.palplato.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserProfileUseCase @Inject constructor(
	private val userRepository: UserRepository
) {
	suspend operator fun invoke(userId: String, profile: UserProfileUpdate): Result<Unit> {
		return userRepository.updateUserProfile(userId, profile)
	}
}