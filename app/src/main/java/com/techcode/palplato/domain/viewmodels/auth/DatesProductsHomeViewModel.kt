package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.domain.usecase.auth.bussiness.mainscreen.GetActiveProductsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import com.techcode.palplato.data.repository.local.SessionManager
import javax.inject.Inject

@HiltViewModel
class DatesProductsHomeViewModel @Inject constructor(
	private val getActiveProductsCountUseCase: GetActiveProductsCountUseCase,
	private val sessionManager: SessionManager
) : ViewModel() {
	
	private val _activeProductsCount = MutableStateFlow(0)
	val activeProductsCount: StateFlow<Int> get() = _activeProductsCount
	
	init {
		observeActiveProducts()
	}
	
	private fun observeActiveProducts() {
		sessionManager.getBusinessId()?.let { businessId ->
			if (businessId.isNotEmpty()) {
				getActiveProductsCountUseCase(businessId)
					.onEach { count ->
						_activeProductsCount.value = count
					}
					.launchIn(viewModelScope)
			}
		}
	}

}
