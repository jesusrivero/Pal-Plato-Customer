package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
	
	
	
	
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Ajustes",
						style = MaterialTheme.typography.titleMedium
					)
				},
				actions = {
					IconButton(onClick = { /* Acción de notificaciones */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification), // ← Asegúrate de tener este ícono
							contentDescription = "Notificaciones", modifier = Modifier.size(25.dp)
						)
					}
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
				.padding(16.dp)
		) {
		
		
		
		

		}
	}

}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
	
	val navController = rememberNavController()
	SettingsScreenContent(navController = navController)
}