package com.techcode.palplato.infraestructure.di

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import com.google.firebase.FirebaseApp
import com.techcode.palplato.presentation.navegation.NavegationHost
import com.techcode.palplato.presentation.theme.PalPlatoTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint // ✅ Requerido por Hilt
class MainActivity : ComponentActivity() {
	
	
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		FirebaseApp.initializeApp(this)
		WindowCompat.setDecorFitsSystemWindows(window, false)
		
		setContent {
			PalPlatoTheme {
				NavegationHost()
			}
		}
	}
}