package com.techcode.palplato.data.repository

import com.google.firebase.auth.AuthResult
import com.techcode.palplato.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl(
	private val auth: FirebaseAuth,
	
	private val firestore: FirebaseFirestore
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
			emit(Resource.Success(result))
		} catch (e: Exception) {
			emit(Resource.Error(e.message ?: "Error al iniciar sesión"))
		}
	}
	
}