package com.techcode.palplato.domain.usecase.auth.updateprofiledates

import com.techcode.palplato.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserPasswordUseCase @Inject constructor(
	private val userRepository: UserRepository
) {
	suspend operator fun invoke(currentPassword: String, newPassword: String): Result<Unit> {
		// Reautenticación obligatoria
		val reauthResult = userRepository.reauthenticateUser(currentPassword)
		if (reauthResult.isFailure) return Result.failure(Exception("Contraseña actual incorrecta"))
		
		// Actualizar contraseña
		return userRepository.updateUserPassword(newPassword)
	}
}
