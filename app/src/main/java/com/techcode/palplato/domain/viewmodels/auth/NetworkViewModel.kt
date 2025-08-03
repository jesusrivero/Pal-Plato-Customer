package com.techcode.palplato.domain.viewmodels.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techcode.palplato.presentation.ui.commons.NetworkMonitor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NetworkViewModel @Inject constructor(
	private val networkMonitor: NetworkMonitor
) : ViewModel() {
	
	private val _isConnected = MutableStateFlow(true)
	val isConnected: StateFlow<Boolean> get() = _isConnected
	
	init {
		networkMonitor.observeNetworkStatus()
			.onEach { connected ->
				_isConnected.value = connected
			}
			.launchIn(viewModelScope)
	}
}
