package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun EditedSecurityScreen(	navController: NavController){
	
	EditedSecurityScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedSecurityScreenContent(navController: NavController) {
	
	var isTwoStepEnabled by rememberSaveable { mutableStateOf(false) }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Seguridad", style = MaterialTheme.typography.titleMedium) },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
					}
				},
				actions = {
					IconButton(onClick = { /* Acción de notificaciones */ }) {
						Icon(
							painter = painterResource(id = R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		}
	) { innerPadding ->
		
		val profileOptions = listOf(
			SecurityItem(
				title = "Contraseña",
				subtitle = "Edita tu contraseña",
				iconRes = R.drawable.ic_person,
				onClick = { navController.navigate(AppRoutes.EditedPasswordScreen) }
			)
		)
		
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
				.padding(horizontal = 16.dp, vertical = 8.dp),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			items(profileOptions) { item ->
				SecutiryOption(
					icon = item.iconRes,
					title = item.title,
					subtitle = item.subtitle,
					onClick = item.onClick
				)
			}
			
			item {
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.clip(RoundedCornerShape(16.dp))
						.padding(vertical = 12.dp, horizontal = 8.dp),
					verticalAlignment = Alignment.CenterVertically
				) {
					Icon(
						painter = painterResource(id = R.drawable.ic_secutiry), // Usa un ícono adecuado
						contentDescription = "Verificación en dos pasos",
						tint = colorScheme.onSurfaceVariant,
						modifier = Modifier
							.size(40.dp)
							.background(
								color = colorScheme.surfaceVariant,
								shape = RoundedCornerShape(12.dp)
							)
							.padding(8.dp)
					)
					
					Spacer(modifier = Modifier.width(16.dp))
					
					Column(modifier = Modifier.weight(1f)) {
						Text("Verificación en dos pasos", style = MaterialTheme.typography.bodyLarge)
						Text(
							"Requiere autenticación adicional al iniciar sesión.",
							style = MaterialTheme.typography.bodySmall,
							color = colorScheme.onSurfaceVariant
						)
					}
					
					Switch(
						checked = isTwoStepEnabled,
						colors = SwitchDefaults.colors(
							checkedThumbColor = colorScheme.primary,
							uncheckedThumbColor = colorScheme.outline,
							checkedTrackColor = colorScheme.primary.copy(alpha = 0.54f),
							uncheckedTrackColor = colorScheme.surfaceVariant
						),
						onCheckedChange = { isTwoStepEnabled = it }
					)
				}
			}
		}
	}
}


@Composable
fun SecutiryOption(
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


data class SecurityItem(
	val title: String,
	val subtitle: String,
	val iconRes: Int,
	val onClick: () -> Unit
)


@Preview(showBackground = true)
@Composable
fun EditedSecurityScreenPreview() {
	
	val navController = rememberNavController()
	EditedSecurityScreenContent(navController = navController)
}