package com.techcode.palplato.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val LightColorScheme = lightColorScheme(
	primary = Color(0xFF1565C0),              // Azul profesional
	onPrimary = Color.White,
	primaryContainer = Color(0xFFE3F2FD),      // Azul muy claro
	onPrimaryContainer = Color(0xFF003C8F),
	
	secondary = Color(0xFF455A64),            // Gris azulado
	onSecondary = Color.White,
	secondaryContainer = Color(0xFFCFD8DC),
	onSecondaryContainer = Color(0xFF263238),
	
	tertiary = Color(0xFF90A4AE),             // Gris claro para detalles
	onTertiary = Color.Black,
	tertiaryContainer = Color(0xFFECEFF1),
	onTertiaryContainer = Color(0xFF263238),
	
	error = Color(0xFFD32F2F),
	onError = Color.White,
	errorContainer = Color(0xFFFFCDD2),
	onErrorContainer = Color(0xFFB71C1C),
	
	background = Color(0xFFFDFDFD),           // Blanco puro, limpio
	onBackground = Color(0xFF1C1C1C),
	
	surface = Color(0xFFFFFFFF),              // Blanco para tarjetas y scaffold
	onSurface = Color(0xFF1C1C1C),
	
	surfaceVariant = Color(0xFFF0F0F0),       // Gris muy claro
	onSurfaceVariant = Color(0xFF3A3A3A),
	
	outline = Color(0xFFB0BEC5)               // Gris suave para bordes
)


private val DarkColorScheme = darkColorScheme(
	primary = Color(0xFF90CAF9),
	onPrimary = Color.Black,
	primaryContainer = Color(0xFF1565C0),
	onPrimaryContainer = Color.White,
	
	secondary = Color(0xFFB0BEC5),
	onSecondary = Color.Black,
	secondaryContainer = Color(0xFF455A64),
	onSecondaryContainer = Color.White,
	
	tertiary = Color(0xFFCFD8DC),
	onTertiary = Color.Black,
	tertiaryContainer = Color(0xFF607D8B),
	onTertiaryContainer = Color.White,
	
	error = Color(0xFFEF9A9A),
	onError = Color(0xFF370001),
	errorContainer = Color(0xFF8C1D18),
	onErrorContainer = Color(0xFFFFDAD4),
	
	background = Color(0xFF121212),
	onBackground = Color(0xFFE0E0E0),
	
	surface = Color(0xFF1E1E1E),
	onSurface = Color(0xFFE0E0E0),
	
	surfaceVariant = Color(0xFF2E2E2E),
	onSurfaceVariant = Color(0xFFCCCCCC),
	
	outline = Color(0xFF757575)
)



@Composable
fun PalPlatoTheme(
	darkTheme: Boolean = isSystemInDarkTheme(),
	dynamicColor: Boolean = false, // lo dejamos en false para mantener el control
	content: @Composable () -> Unit
) {
	val colorScheme = when {
		dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
			val context = LocalContext.current
			if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
		}
		darkTheme -> DarkColorScheme
		else -> LightColorScheme
	}
	
	MaterialTheme(
		colorScheme = colorScheme,
		typography = Typography,
		content = content
	)
}
