package com.techcode.palplato.domain.viewmodels.auth

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.GetUserProfileUseCase
import com.techcode.palplato.domain.model.UserProfileUpdate
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.ReauthenticateUserUseCase
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.UpdateUserEmailUseCase
import com.techcode.palplato.domain.usecase.auth.updateprofiledates.UpdateUserProfileUseCase
import com.techcode.palplato.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
	private val updateUserProfileUseCase: UpdateUserProfileUseCase,
	private val getUserProfileUseCase: GetUserProfileUseCase,
	private val reauthenticateUserUseCase: ReauthenticateUserUseCase,
	private val updateUserEmailUseCase: UpdateUserEmailUseCase,
	private val sessionManager: SessionManager
) : ViewModel() {
	
	private val _updateProfileState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val updateProfileState: StateFlow<Resource<Unit>> = _updateProfileState
	
	private val _userProfileState = MutableStateFlow<Resource<UserProfileUpdate>>(Resource.Idle)
	val userProfileState = _userProfileState.asStateFlow()
	
	private val _updateEmailState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val updateEmailState: StateFlow<Resource<Unit>> = _updateEmailState
	
	
	fun updateUserProfile(name: String, lastname: String, email: String, password: String) {
		viewModelScope.launch {
			_updateProfileState.value = Resource.Loading()
			
			val reauthResult = reauthenticateUserUseCase(password)
			if (reauthResult.isFailure) {
				_updateProfileState.value = Resource.Error("Contraseña incorrecta o sesión expirada")
				return@launch
			}
			
			val userId = sessionManager.getUserUid()
				?: return@launch run { _updateProfileState.value = Resource.Error("Usuario no autenticado") }
			
			val profile = UserProfileUpdate(name, lastname, email)
			val result = updateUserProfileUseCase(userId, profile)
			
			_updateProfileState.value = if (result.isSuccess) {
				Resource.Success(Unit)
			} else {
				Resource.Error(result.exceptionOrNull()?.message ?: "Error al actualizar perfil")
			}
		}
	}
	
	fun getUserProfile() {
		viewModelScope.launch {
			_userProfileState.value = Resource.Loading()
			val userId = sessionManager.getUserUid()
				?: return@launch run { _userProfileState.value = Resource.Error("Usuario no autenticado") }
			
			val result = getUserProfileUseCase(userId)
			_userProfileState.value = if (result.isSuccess) {
				Resource.Success(result.getOrThrow())
			} else {
				Resource.Error(result.exceptionOrNull()?.message ?: "Error al obtener perfil")
			}
		}
	}
	
	fun updateUserEmail(newEmail: String, password: String) {
		viewModelScope.launch {
			_updateEmailState.value = Resource.Loading()
			
			try {
				val currentUser = FirebaseAuth.getInstance().currentUser
				val providers = currentUser?.providerData?.map { it.providerId } ?: emptyList()
				Log.d("UpdateEmail", "Proveedores de autenticación: $providers")
				
				if (!providers.contains(EmailAuthProvider.PROVIDER_ID)) {
					_updateEmailState.value =
						Resource.Error("No puedes cambiar el correo porque no es una cuenta de Email/Password")
					return@launch
				}
				
				// Reautenticación
				val reauthResult = reauthenticateUserUseCase(password)
				if (reauthResult.isFailure) {
					_updateEmailState.value = Resource.Error("Contraseña incorrecta o sesión expirada")
					return@launch
				}
				
				Log.d("UpdateEmail", "Reautenticación exitosa")
				
				// Enviar correo de verificación
				val result = updateUserEmailUseCase(newEmail)
				_updateEmailState.value = if (result.isSuccess) {
					Log.d("UpdateEmail", "Correo de verificación enviado")
					Resource.Success(Unit) // Mostramos mensaje: "Revisa tu correo para confirmar el cambio"
				} else {
					val errorMsg = result.exceptionOrNull()?.message ?: "Error al enviar verificación"
					Log.e("UpdateEmail", errorMsg)
					Resource.Error(errorMsg)
				}
			} catch (e: Exception) {
				Log.e("UpdateEmail", "Excepción al actualizar correo: ${e.message}", e)
				_updateEmailState.value = Resource.Error(e.message ?: "Error desconocido")
			}
		}
	}
	
	
	fun resetUpdateEmailState() {
		_updateEmailState.value = Resource.Idle
	}
	
	fun resetUpdateProfileState() {
		_updateProfileState.value = Resource.Idle
	}
}
