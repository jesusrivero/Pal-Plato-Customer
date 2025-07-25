package com.techcode.palplato.infraestructure.di

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.techcode.palplato.presentation.navegation.NavegationHost
import com.techcode.palplato.presentation.theme.PalPlatoTheme

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
		
		setContent {
			PalPlatoTheme {
				NavegationHost()
				
			}
		}
	}
}