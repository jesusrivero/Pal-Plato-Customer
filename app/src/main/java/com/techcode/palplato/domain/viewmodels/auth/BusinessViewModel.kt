package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.usecase.auth.bussiness.CreateBusinessUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.GetBusinessUseCase
import com.techcode.palplato.domain.usecase.auth.bussiness.UpdateBusinessUseCase
import com.techcode.palplato.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel @Inject constructor(
	private val sessionManager: SessionManager, // Asegúrate de tener SessionManager inyectado
	private val createBusinessUseCase: CreateBusinessUseCase,
	private val updateBusinessUseCase: UpdateBusinessUseCase,
	private val getBusinessUseCase: GetBusinessUseCase,
) : ViewModel() {
	
	private val _businessState = MutableStateFlow<Resource<Unit>>(Resource.Idle) // 👈 Estado inicial en Idle
	val businessState: StateFlow<Resource<Unit>> = _businessState
	
	private val _updateBusinessState = MutableStateFlow<Resource<Unit>>(Resource.Idle)
	val updateBusinessState: StateFlow<Resource<Unit>> = _updateBusinessState
	
	
	private val _businessData = MutableStateFlow<Business?>(null)
	val businessData: StateFlow<Business?> = _businessData
	
	fun createBusiness(business: Business) {
		viewModelScope.launch {
			_businessState.value = Resource.Loading()
			val result = createBusinessUseCase(business)
			if (result is Resource.Success) {
				sessionManager.saveBusinessId(result.result)
				_businessState.value = Resource.Success(Unit)
			} else if (result is Resource.Error) {
				_businessState.value = Resource.Error(result.message)
			}
		}
	}
	
	fun updateBusiness(updates: Map<String, Any>) {
		viewModelScope.launch {
			_businessState.value = Resource.Loading()
			val businessId = sessionManager.getBusinessId()
			if (businessId != null) {
				val result = updateBusinessUseCase(businessId, updates)
				_businessState.value = result
			} else {
				_businessState.value = Resource.Error("No se encontró el ID del negocio")
			}
		}
	}
	
	fun getBusinessData() {
		viewModelScope.launch {
			val businessId = sessionManager.getBusinessId()
			if (businessId != null) {
				_businessData.value = getBusinessUseCase(businessId)
			}
		}
	}
	
	fun resetState() {
		_businessState.value = Resource.Idle // 👈 Reinicia el estado después de manejar el resultado
	}
}
