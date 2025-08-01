package com.techcode.palplato.domain.repository

import com.google.firebase.auth.AuthResult
import com.techcode.palplato.domain.model.User
import kotlinx.coroutines.flow.Flow
import com.techcode.palplato.utils.Resource

interface AuthRepository {
	suspend fun registerUser(user: User, password: String): Flow<Resource<AuthResult>>
	
	suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>>
	
	suspend fun getBusinessIdForUser(uid: String): String?
	suspend fun syncUserEmailIfNeeded(): Result<Unit> // ✅ NUEVA FUNCIÓN
}
