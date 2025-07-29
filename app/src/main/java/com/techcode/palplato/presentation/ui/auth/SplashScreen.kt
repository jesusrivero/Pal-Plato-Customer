package com.techcode.palplato.presentation.ui.auth

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.techcode.palplato.domain.viewmodels.auth.AuthViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun SplashScreen(
	navController: NavController,
	viewModel: AuthViewModel = hiltViewModel()
) {
	LaunchedEffect(Unit) {
		kotlinx.coroutines.delay(300)
		
		val isLoggedIn = viewModel.isLoggedIn()
		Log.d("SplashScreen", "¿El usuario está logueado?: $isLoggedIn")
		
		if (isLoggedIn) {
			navController.navigate(AppRoutes.MainScreen) {
				popUpTo(AppRoutes.SplashScreen) { inclusive = true }
			}
		} else {
			navController.navigate(AppRoutes.LoginScreen) {
				popUpTo(AppRoutes.SplashScreen) { inclusive = true }
			}
		}
	}
	
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		CircularProgressIndicator()
	}
}
