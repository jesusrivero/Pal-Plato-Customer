package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun SettingsScreen(	navController: NavController){
	
	SettingsScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(navController: NavController) {
	val items = listOf(
		SettingsItem("Perfil", "Cambia tu nombre, correo y teléfono", com.techcode.palplato.R.drawable.ic_person) {
			// Acción al navegar a Perfil
		},
		SettingsItem("Negocio", "Actualiza el nombre, tipo de comida y dirección de tu negocio",  com.techcode.palplato.R.drawable.ic_bussines) {
			// Acción al navegar a Negocio
		},
		SettingsItem("Seguridad", "Cambia tu contraseña y activa la verificación en dos pasos",  com.techcode.palplato.R.drawable.ic_secutiry) {
			// Acción al navegar a Seguridad
		},
		SettingsItem("Notificaciones", "Configura las notificaciones push y elige cuáles recibir",  com.techcode.palplato.R.drawable.ic_notification) {
			// Acción al navegar a Notificaciones
		}
	)
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Ajustes",
						style = MaterialTheme.typography.titleMedium
					)
				}
			)
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 16.dp, vertical = 8.dp)
		) {
			items.forEach { item ->
				SettingOption(
					icon = item.iconRes,
					title = item.title,
					subtitle = item.subtitle,
					onClick = item.onClick
				)
				Spacer(modifier = Modifier.height(8.dp))
			}
			
			// Opción Cerrar sesión
			Divider(modifier = Modifier.padding(vertical = 8.dp))
			SettingOption(
				icon =  com.techcode.palplato.R.drawable.ic_logout, // ← Usa tu icono de logout
				title = "Cerrar sesión",
				subtitle = "",
				onClick = {
					// Acción para cerrar sesión
				}
			)
		}
	}
}

// Data class para manejar cada ítem
data class SettingsItem(
	val title: String,
	val subtitle: String,
	val iconRes: Int,
	val onClick: () -> Unit
)

@Composable
fun SettingOption(
	icon: Int,
	title: String,
	subtitle: String,
	onClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.clip(RoundedCornerShape(16.dp))
			.clickable(
				onClick = onClick,
				interactionSource = remember { MutableInteractionSource() },
				indication = LocalIndication.current // ← Usa la implementación del tema actual
			)
			.padding(vertical = 12.dp, horizontal = 8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Icon(
			painter = painterResource(id = icon),
			contentDescription = title,
			tint = MaterialTheme.colorScheme.onSurfaceVariant,
			modifier = Modifier
				.size(40.dp)
				.background(
					color = MaterialTheme.colorScheme.surfaceVariant,
					shape = RoundedCornerShape(12.dp)
				)
				.padding(8.dp)
		)
		
		Spacer(modifier = Modifier.width(16.dp))
		
		Column(modifier = Modifier.weight(1f)) {
			Text(text = title, style = MaterialTheme.typography.bodyLarge)
			if (subtitle.isNotEmpty()) {
				Text(
					text = subtitle,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
		
		Icon(
			imageVector = Icons.Default.KeyboardArrowRight,
			contentDescription = "Ir a $title",
			tint = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
}



@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
	
	val navController = rememberNavController()
	SettingsScreenContent(navController = navController)
}