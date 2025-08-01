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
	primary = Color(0xFFFFC107),              // Amarillo (botones, barra inferior)
	onPrimary = Color.Black,
	primaryContainer = Color(0xFFFFECB3),
	onPrimaryContainer = Color(0xFF3E2F00),
	
	secondary = Color(0xFFE64A19),            // Naranja/rojo para acciones
	onSecondary = Color.White,
	secondaryContainer = Color(0xFFFFCCBC),
	onSecondaryContainer = Color(0xFF4A1F0F),
	
	tertiary = Color(0xFFF5F5F5),             // Gris claro para fondos secundarios
	onTertiary = Color(0xFF212121),
	
	error = Color(0xFFD32F2F),
	onError = Color.White,
	
	background = Color(0xFFFFFFFF),
	onBackground = Color(0xFF212121),
	
	surface = Color(0xFFFFFFFF),
	onSurface = Color(0xFF212121),
	
	surfaceVariant = Color(0xFFF0F0F0),
	onSurfaceVariant = Color(0xFF424242),
	
	outline = Color(0xFFBDBDBD)
)


private val DarkColorScheme = darkColorScheme(
	primary = Color(0xFFFFC107),
	onPrimary = Color.Black,
	primaryContainer = Color(0xFF795600),
	onPrimaryContainer = Color(0xFFFFECB3),
	
	secondary = Color(0xFFE64A19),
	onSecondary = Color.White,
	secondaryContainer = Color(0xFF5D1900),
	onSecondaryContainer = Color(0xFFFFCCBC),
	
	tertiary = Color(0xFF2C2C2C),
	onTertiary = Color.White,
	
	error = Color(0xFFEF9A9A),
	onError = Color(0xFF370001),
	
	background = Color(0xFF121212),
	onBackground = Color(0xFFE0E0E0),
	
	surface = Color(0xFF1E1E1E),
	onSurface = Color(0xFFE0E0E0),
	
	surfaceVariant = Color(0xFF2E2E2E),
	onSurfaceVariant = Color(0xFFCCCCCC),
	
	outline = Color(0xFF8D6E63)
)



//private val LightColorScheme = lightColorScheme(
//	primary = Color(0xFFFF5722),              // Naranja intenso (principal)
//	onPrimary = Color.White,
//	primaryContainer = Color(0xFFFFCCBC),     // Naranja claro para contenedores
//	onPrimaryContainer = Color(0xFF4E0A00),
//
//	secondary = Color(0xFFBF360C),            // Naranja oscuro para acentos
//	onSecondary = Color.White,
//	secondaryContainer = Color(0xFFFF8A65),
//	onSecondaryContainer = Color(0xFF3E0E00),
//
//	tertiary = Color(0xFFFF7043),             // Naranja intermedio para detalles
//	onTertiary = Color.White,
//	tertiaryContainer = Color(0xFFFFAB91),
//	onTertiaryContainer = Color(0xFF4E0A00),
//
//	error = Color(0xFFD32F2F),
//	onError = Color.White,
//	errorContainer = Color(0xFFFFCDD2),
//	onErrorContainer = Color(0xFFB71C1C),
//
//	background = Color(0xFFFFFBF8),           // Blanco cálido con ligero toque naranja
//	onBackground = Color(0xFF1C1C1C),
//
//	surface = Color(0xFFFFFFFF),              // Blanco puro
//	onSurface = Color(0xFF1C1C1C),
//
//	surfaceVariant = Color(0xFFFFEDE7),       // Fondo suave anaranjado
//	onSurfaceVariant = Color(0xFF5D4037),
//
//	outline = Color(0xFFBCAAA4)               // Marrón grisáceo para bordes
//)
//
//private val DarkColorScheme = darkColorScheme(
//	primary = Color(0xFFFF8A65),              // Naranja más suave en dark mode
//	onPrimary = Color.Black,
//	primaryContainer = Color(0xFFFF5722),
//	onPrimaryContainer = Color.White,
//
//	secondary = Color(0xFFFFAB91),
//	onSecondary = Color.Black,
//	secondaryContainer = Color(0xFFBF360C),
//	onSecondaryContainer = Color.White,
//
//	tertiary = Color(0xFFFF7043),
//	onTertiary = Color.White,
//	tertiaryContainer = Color(0xFFBF360C),
//	onTertiaryContainer = Color.White,
//
//	error = Color(0xFFEF9A9A),
//	onError = Color(0xFF370001),
//	errorContainer = Color(0xFF8C1D18),
//	onErrorContainer = Color(0xFFFFDAD4),
//
//	background = Color(0xFF121212),
//	onBackground = Color(0xFFE0E0E0),
//
//	surface = Color(0xFF1E1E1E),
//	onSurface = Color(0xFFE0E0E0),
//
//	surfaceVariant = Color(0xFF2E2E2E),
//	onSurfaceVariant = Color(0xFFCCCCCC),
//
//	outline = Color(0xFF757575)
//)


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
