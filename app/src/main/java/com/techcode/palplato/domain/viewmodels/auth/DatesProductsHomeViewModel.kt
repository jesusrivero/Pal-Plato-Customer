package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.techcode.palplato.data.repository.local.SessionManager
import javax.inject.Inject

@HiltViewModel
class DatesProductsHomeViewModel @Inject constructor(
	private val sessionManager: SessionManager
) : ViewModel() {
	
	private val _activeProductsCount = MutableStateFlow(0)
	val activeProductsCount: StateFlow<Int> get() = _activeProductsCount


}
