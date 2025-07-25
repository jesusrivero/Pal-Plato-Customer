package com.techcode.palplato.presentation.ui.menu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun MenuScreen(	navController: NavController){
	
	MenuScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreenContent(navController: NavController) {
	
	
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Menús",
						style = MaterialTheme.typography.titleMedium
					)
				},
				actions = {
					IconButton(onClick = { }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
							contentDescription = "Notificaciones", modifier = Modifier.size(25.dp)
						)
					}
				},
			)
		},floatingActionButton = {
			FloatingActionButton(
				shape = CircleShape,
				onClick = {  },
				containerColor = Color.Black,
				contentColor = Color.White
			) {
				Icon(
					modifier = Modifier.size(22.dp),
					imageVector = Icons.Default.Add,
					contentDescription = "Agregar"
				)
			}
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		}
	)  { innerPadding ->
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
fun MenuScreenPreview() {
	
	val navController = rememberNavController()
	MenuScreenContent(navController = navController)
}