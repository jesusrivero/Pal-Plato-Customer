package com.techcode.palplato.data.repository

import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.UserProfileUpdate
import com.techcode.palplato.domain.repository.UserRepository
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
	private val firestore: FirebaseFirestore,
	private val auth: FirebaseAuth,
	private val sessionManager: SessionManager

) : UserRepository {
	
	override suspend fun updateUserProfile(userId: String, profile: UserProfileUpdate): Result<Unit> {
		return try {
			val updates = mapOf(
				"name" to profile.name,
				"lastname" to profile.lastname,
			)
			
			firestore.collection("users")
				.document(userId)
				.update(updates)
				.await()
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
	
	override suspend fun getUserProfile(userId: String): Result<UserProfileUpdate> {
		return try {
			val snapshot = firestore.collection("users")
				.document(userId)
				.get()
				.await()
			
			if (snapshot.exists()) {
				val name = snapshot.getString("name") ?: ""
				val lastname = snapshot.getString("lastname") ?: ""
				val email = snapshot.getString("email") ?: ""
				Result.success(UserProfileUpdate(name, lastname, email))
			} else {
				Result.failure(Exception("Usuario no encontrado"))
			}
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
	
	override suspend fun updateUserEmail(newEmail: String): Result<Unit> {
		return try {
			val currentUser = auth.currentUser
				?: return Result.failure(Exception("Usuario no autenticado"))
			
			// ✅ Solo enviamos correo de verificación, sin actualizar Firestore todavía
			currentUser.verifyBeforeUpdateEmail(newEmail).await()
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
	
	
	override suspend fun reauthenticateUser(password: String): Result<Unit> {
		return try {
			val currentUser = auth.currentUser
				?: return Result.failure(Exception("Usuario no autenticado"))
			
			val email = currentUser.email
				?: return Result.failure(Exception("Correo no disponible"))
			
			val credential = EmailAuthProvider.getCredential(email, password)
			currentUser.reauthenticate(credential).await()
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
	
	// ✅ Nueva función: sincronizar email confirmado con Firestore
	override suspend fun syncUserEmail(): Result<Unit> {
		return try {
			val currentUser = auth.currentUser
				?: return Result.failure(Exception("Usuario no autenticado"))
			val verifiedEmail = currentUser.email
				?: return Result.failure(Exception("Correo no disponible"))
			
			val userId = sessionManager.getUserUid()
				?: return Result.failure(Exception("Usuario no autenticado"))
			
			firestore.collection("users")
				.document(userId)
				.update("email", verifiedEmail)
				.await()
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
	
	override suspend fun updateUserPassword(newPassword: String): Result<Unit> {
		return try {
			val currentUser = auth.currentUser
				?: return Result.failure(Exception("Usuario no autenticado"))
			
			currentUser.updatePassword(newPassword).await()
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}
}
