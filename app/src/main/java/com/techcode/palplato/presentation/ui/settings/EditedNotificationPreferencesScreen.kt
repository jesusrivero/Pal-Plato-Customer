package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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

@Composable
fun EditedNotificationPreferencesScreen(	navController: NavController){
	
	EditedNotificationPreferencesScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedNotificationPreferencesScreenContent(navController: NavController) {
	var pushNotificationsEnabled by rememberSaveable { mutableStateOf(true) }
	var emailNotificationsEnabled by rememberSaveable { mutableStateOf(false) }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Notificaciones",
						style = MaterialTheme.typography.titleMedium
					)
				},
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
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(16.dp),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			NotificationPreferenceItem(
				title = "Notificaciones Push",
				description = "Recibir notificaciones en tu dispositivo",
				checked = pushNotificationsEnabled,
				onCheckedChange = { pushNotificationsEnabled = it }
			)
			
			NotificationPreferenceItem(
				title = "Notificaciones por Correo",
				description = "Recibir correos sobre novedades y alertas",
				checked = emailNotificationsEnabled,
				onCheckedChange = { emailNotificationsEnabled = it }
			)
		}
	}
}

@Composable
fun NotificationPreferenceItem(
	title: String,
	description: String,
	checked: Boolean,
	onCheckedChange: (Boolean) -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(vertical = 8.dp),
		verticalAlignment = Alignment.CenterVertically
	) {
		Column(modifier = Modifier.weight(1f)) {
			Text(text = title, style = MaterialTheme.typography.bodyLarge)
			Text(
				text = description,
				style = MaterialTheme.typography.bodySmall,
				color = colorScheme.onSurfaceVariant
			)
		}
		
		Switch(
			checked = checked,
			onCheckedChange = onCheckedChange,
			colors = SwitchDefaults.colors(
				checkedThumbColor = colorScheme.primary,
				uncheckedThumbColor = colorScheme.outline,
				checkedTrackColor = colorScheme.primary.copy(alpha = 0.54f),
				uncheckedTrackColor = colorScheme.surfaceVariant
			)
		)
	}
}



@Preview(showBackground = true)
@Composable
fun EditedNotificationPreferencesScreenPreview() {
	
	val navController = rememberNavController()
	EditedNotificationPreferencesScreenContent(navController = navController)
}