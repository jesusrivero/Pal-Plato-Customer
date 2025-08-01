package com.techcode.palplato.data.repository

import com.google.firebase.auth.AuthResult
import com.techcode.palplato.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
	private val auth: FirebaseAuth,
	private val firestore: FirebaseFirestore,
	private val sessionManager: SessionManager,
) : AuthRepository {
	
	override suspend fun registerUser(user: User, password: String): Flow<Resource<AuthResult>> = flow {
		emit(Resource.Loading())
		try {
			val result = auth.createUserWithEmailAndPassword(user.email, password).await()
			
			val uid = result.user?.uid ?: throw Exception("UID no disponible")
			
			val userData = user.copy(uid = uid)
			
			firestore.collection("users")
				.document(uid)
				.set(userData)
				.await()
			
			emit(Resource.Success(result))
		} catch (e: Exception) {
			emit(Resource.Error(e.message ?: "Error desconocido"))
		}
	}
	
	override suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
		emit(Resource.Loading())
		try {
			val result = auth.signInWithEmailAndPassword(email, password).await()
			
			// ✅ Guardar UID en SessionManager
			val userId = auth.currentUser?.uid ?: throw Exception("Usuario no autenticado")
			sessionManager.saveSession(userId)
			
			// ✅ Recuperar el businessId desde Firestore y guardarlo
			val document = firestore.collection("users").document(userId).get().await()
			val businessId = document.getString("businessId") ?: ""
			sessionManager.saveBusinessId(businessId)
			
			emit(Resource.Success(result))
		} catch (e: Exception) {
			emit(Resource.Error(e.message ?: "Error al iniciar sesión"))
		}
	}
	
	override suspend fun getBusinessIdForUser(uid: String): String? {
		return try {
			val snapshot = firestore.collection("businesses")
				.whereEqualTo("ownerId", uid)
				.get()
				.await()
			
			if (!snapshot.isEmpty) {
				snapshot.documents.first().id
			} else null
		} catch (e: Exception) {
			null
		}
	}
	
	override suspend fun syncUserEmailIfNeeded(): Result<Unit> {
		return try {
			val currentUser = auth.currentUser
				?: return Result.failure(Exception("Usuario no autenticado"))
			
			val userId = sessionManager.getUserUid()
				?: return Result.failure(Exception("Usuario no autenticado"))
			
			// Obtener el email en Firestore
			val userDoc = firestore.collection("users").document(userId).get().await()
			val firestoreEmail = userDoc.getString("email")
			val authEmail = currentUser.email
			
			// Si el correo de Auth y Firestore son distintos, actualizamos
			if (authEmail != null && firestoreEmail != authEmail) {
				firestore.collection("users")
					.document(userId)
					.update("email", authEmail)
					.await()
			}
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}


//	override suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> = flow {
//		emit(Resource.Loading())
//		try {
//			val result = auth.signInWithEmailAndPassword(email, password).await()
//			emit(Resource.Success(result))
//		} catch (e: Exception) {
//			emit(Resource.Error(e.message ?: "Error al iniciar sesión"))
//		}
//	}
	
}