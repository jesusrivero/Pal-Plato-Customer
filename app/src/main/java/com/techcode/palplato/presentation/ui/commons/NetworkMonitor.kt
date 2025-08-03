package com.techcode.palplato.presentation.ui.commons

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMonitor @Inject constructor(
	@ApplicationContext private val context: Context
) {
	private val connectivityManager =
		context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	
	fun observeNetworkStatus(): Flow<Boolean> = callbackFlow {
		val networkCallback = object : ConnectivityManager.NetworkCallback() {
			override fun onAvailable(network: Network) {
				trySend(true)
			}
			
			override fun onLost(network: Network) {
				trySend(false)
			}
		}
		
		// Verificamos el estado actual al iniciar
		val isConnected = connectivityManager.activeNetwork?.let { network ->
			val capabilities = connectivityManager.getNetworkCapabilities(network)
			capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
		} ?: false
		trySend(isConnected)
		
		connectivityManager.registerDefaultNetworkCallback(networkCallback)
		
		awaitClose { connectivityManager.unregisterNetworkCallback(networkCallback) }
	}
}
