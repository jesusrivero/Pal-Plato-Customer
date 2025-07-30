package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.usecase.auth.bussiness.CreateBusinessUseCase
import com.techcode.palplato.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessViewModel @Inject constructor(
	private val createBusinessUseCase: CreateBusinessUseCase
) : ViewModel() {
	
	private val _businessState = MutableStateFlow<Resource<Unit>>(Resource.Idle) // 👈 Estado inicial en Idle
	val businessState: StateFlow<Resource<Unit>> = _businessState
	
	fun createBusiness(business: Business) {
		viewModelScope.launch {
			_businessState.value = Resource.Loading()
			val result = createBusinessUseCase(business)
			_businessState.value = result
		}
	}
	
	fun resetState() {
		_businessState.value = Resource.Idle // 👈 Reinicia el estado después de manejar el resultado
	}
}
