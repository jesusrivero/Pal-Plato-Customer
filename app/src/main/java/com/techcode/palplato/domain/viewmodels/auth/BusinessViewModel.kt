package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.usecase.auth.bussiness.GetBusinessesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach


@HiltViewModel
class BusinessViewModel @Inject constructor(
	private val getBusinessesUseCase: GetBusinessesUseCase,
	private val sessionManager: SessionManager
) : ViewModel() {
	
	private val _businesses = MutableStateFlow<List<Business>>(emptyList())
	val businesses: StateFlow<List<Business>> = _businesses
	
	init {
		fetchBusinesses()
	}
	
	fun fetchBusinesses(businessId: String? = null) {
		getBusinessesUseCase(businessId).onEach { list ->
			_businesses.value = list
		}.launchIn(viewModelScope)
	}
	
	fun fetchBusinessById(businessId: String): Flow<Business?> {
		return getBusinessesUseCase(businessId).map { it.firstOrNull() }
	}
}



//	private fun fetchBusinesses() {
//		getBusinessesUseCase().onEach { list ->
//			_businesses.value = list
//		}.launchIn(viewModelScope)
//	}
	
	

	

