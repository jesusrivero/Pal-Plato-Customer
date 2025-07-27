package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R


@Composable
fun EditedNameScreen(	navController: NavController){
	
	EditedNameScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedNameScreenContent(navController: NavController) {
	var firstName by rememberSaveable { mutableStateOf("") }
	var lastName by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Nombre completo",
						style = MaterialTheme.typography.titleMedium
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
					}
				},
				actions = {
					IconButton(onClick = { }) {
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
			
			OutlinedTextField(
				value = firstName,
				onValueChange = { firstName = it },
				label = { Text("Nombres") },
				shape = RoundedCornerShape(16.dp),
				singleLine = true,
				modifier = Modifier.fillMaxWidth()
			)
			
			OutlinedTextField(
				value = lastName,
				onValueChange = { lastName = it },
				label = { Text("Apellidos") },
				shape = RoundedCornerShape(16.dp),
				singleLine = true,
				modifier = Modifier.fillMaxWidth()
			)
			
			Divider()
			
			OutlinedTextField(
				value = password,
				onValueChange = { password = it },
				label = { Text("Contraseña actual") },
				shape = RoundedCornerShape(16.dp),
				singleLine = true,
				visualTransformation = PasswordVisualTransformation(),
				modifier = Modifier.fillMaxWidth()
			)
			
			Button(
				onClick = {
					// Aquí puedes validar los campos y realizar la operación
				},
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Guardar cambios")
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
fun EditedNameScreenPreview() {
	
	val navController = rememberNavController()
	EditedNameScreenContent(navController = navController)
}