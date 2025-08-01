package com.techcode.palplato.domain.viewmodels.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.User
import com.techcode.palplato.domain.repository.AuthRepository
import com.techcode.palplato.domain.repository.UserRepository
import com.techcode.palplato.domain.usecase.auth.LoginUseCase
import com.techcode.palplato.domain.usecase.auth.RegisterUseCase
import com.techcode.palplato.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
	private val sessionManager: SessionManager,
	private val registerUseCase: RegisterUseCase,
	private val firestore: FirebaseFirestore,
	private val authRepository: AuthRepository,
	private val loginUseCase: LoginUseCase,
	private val userRepository: UserRepository
) : ViewModel() {
	
	private val _loginState = MutableStateFlow<Resource<AuthResult>?>(null)
	val loginState: StateFlow<Resource<AuthResult>?> = _loginState
	
	private val _resetPasswordState = MutableStateFlow<Resource<Boolean>?>(null)
	val resetPasswordState: StateFlow<Resource<Boolean>?> = _resetPasswordState
	
	private val _registerState = MutableStateFlow<Resource<AuthResult>?>(null)
	val registerState: StateFlow<Resource<AuthResult>?> = _registerState.asStateFlow()
	
	fun register(user: User, password: String) {
		viewModelScope.launch {
			registerUseCase(user, password).collect { result ->
				_registerState.value = result
				
				if (result is Resource.Success) {
					val uid = result.result.user?.uid
					if (uid != null) {
						// ✅ Guardar UID en sesión
						sessionManager.saveSession(uid)
						Log.d("RegisterViewModel", "UID guardado: $uid")
						
						// ✅ Obtener businessId (si existe, aunque en registro normalmente no habrá)
						val businessId = authRepository.getBusinessIdForUser(uid)
						if (businessId != null) {
							sessionManager.saveBusinessId(businessId)
							Log.d("RegisterViewModel", "Business ID guardado: $businessId")
						} else {
							Log.w("RegisterViewModel", "No se encontró businessId, el usuario debe crear uno")
						}
					} else {
						Log.e("RegisterViewModel", "UID es nulo, no se puede guardar sesión")
					}
				}
			}
		}
	}
	
	fun login(email: String, password: String) {
		viewModelScope.launch {
			loginUseCase(email, password).collect { result ->
				Log.d("AuthViewModel", "Resultado login: $result")
				_loginState.value = result
				
				if (result is Resource.Success) {
					val uid = result.result.user?.uid
					Log.d("AuthViewModel", "UID obtenido: $uid")
					
					if (uid == null) {
						Log.e("AuthViewModel", "UID es nulo, no se puede guardar sesión")
						return@collect
					}
					
					// Guardar sesión
					sessionManager.saveSession(uid)
					Log.d("AuthViewModel", "Sesión guardada: uid=$uid, loggedIn=${sessionManager.isLoggedIn()}")
					
					// ✅ Esperar a que termine la sincronización
					val syncResult = authRepository.syncUserEmailIfNeeded()
					if (syncResult.isSuccess) {
						Log.d("AuthViewModel", "Correo sincronizado correctamente con Firestore")
					} else {
						Log.e("AuthViewModel", "Error al sincronizar email: ${syncResult.exceptionOrNull()?.message}")
					}
					
					
					// ✅ Obtener businessId
					val businessId = authRepository.getBusinessIdForUser(uid)
					if (businessId != null) {
						sessionManager.saveBusinessId(businessId)
						Log.d("AuthViewModel", "Business ID guardado: $businessId")
					} else {
						Log.w("AuthViewModel", "No se encontró businessId, el usuario debe crear uno")
					}
				}
			}
		}
	}

	
//	fun login(email: String, password: String) {
//		viewModelScope.launch {
//			loginUseCase(email, password).collect { result ->
//				Log.d("AuthViewModel", "Resultado login: $result")
//				_loginState.value = result
//
//				if (result is Resource.Success) {
//					val uid = result.result.user?.uid
//					Log.d("AuthViewModel", "UID obtenido: $uid")
//
//					if (uid == null) {
//						Log.e("AuthViewModel", "UID es nulo, no se puede guardar sesión")
//						return@collect
//					}
//
//					sessionManager.saveSession(uid)
//					Log.d("AuthViewModel", "Sesión guardada: uid=$uid, loggedIn=${sessionManager.isLoggedIn()}")
//
//					val businessId = authRepository.getBusinessIdForUser(uid) // ✅ ahora sí existe
//					if (businessId != null) {
//						sessionManager.saveBusinessId(businessId)
//						Log.d("AuthViewModel", "Business ID guardado: $businessId")
//					} else {
//						Log.w("AuthViewModel", "No se encontró businessId, el usuario debe crear uno")
//					}
//				}
//			}
//		}
//	}



//
//	fun login(email: String, password: String) {
//		viewModelScope.launch {
//			loginUseCase(email, password).collect { result ->
//				Log.d("AuthViewModel", "Resultado login: $result")
//				_loginState.value = result
//
//				if (result is Resource.Success) {
//					val uid = result.result.user?.uid
//					Log.d("AuthViewModel", "UID obtenido: $uid")
//
//					if (uid == null) {
//						Log.e("AuthViewModel", "UID es nulo, no se puede guardar sesión")
//						return@collect
//					}
//
//					// Guardar sesión solo con UID
//					sessionManager.saveSession(uid)
//					Log.d("AuthViewModel", "Sesión guardada: uid=$uid, loggedIn=${sessionManager.isLoggedIn()}")
//				}
//			}
//		}
//	}
	
	fun resetPassword(email: String) {
		viewModelScope.launch {
			_resetPasswordState.value = Resource.Loading()
			try {
				FirebaseAuth.getInstance().sendPasswordResetEmail(email).await()
				_resetPasswordState.value = Resource.Success(true)
			} catch (e: Exception) {
				_resetPasswordState.value = Resource.Error(e.message ?: "Error al enviar correo de recuperación")
			}
		}
	}
	
	fun clearResetPasswordState() {
		_resetPasswordState.value = null
	}
	
	fun clearState() {
		_registerState.value = null
		_loginState.value = Resource.Loading()
	}
	
	fun resetLoginState() {
		_loginState.value = null // si tu state es MutableStateFlow<Resource<AuthResult>?>
	}
	
	fun logout() {
		viewModelScope.launch {
			sessionManager.clearSession()
			FirebaseAuth.getInstance().signOut()
			Log.d("AuthViewModel", "Sesión guardada: loggedIn=${sessionManager.isLoggedIn()}")
		}
	}
	
	fun isLoggedIn(): Boolean {
		return sessionManager.isLoggedIn()
	}
	
}

		
		
		
		
		
		