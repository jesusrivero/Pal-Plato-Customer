package com.techcode.palplato.domain.repository

import com.techcode.palplato.domain.model.UserProfileUpdate

interface UserRepository {
	suspend fun updateUserProfile(userId: String, profile: UserProfileUpdate): Result<Unit>
	suspend fun getUserProfile(userId: String): Result<UserProfileUpdate>
	suspend fun reauthenticateUser(password: String): Result<Unit>
	suspend fun updateUserEmail(newEmail: String): Result<Unit>
	suspend fun syncUserEmail(): Result<Unit>

}