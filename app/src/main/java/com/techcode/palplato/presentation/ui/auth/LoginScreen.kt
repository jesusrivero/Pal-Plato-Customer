package com.techcode.palplato.presentation.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar



@Composable
fun LoginScreen(navController: NavController){
	
	LoginScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(navController: NavController) {
	
	// Estados
	var email by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }
	var isLoading by rememberSaveable { mutableStateOf(false) }
	var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = "", style = MaterialTheme.typography.titleMedium) },
				actions = {
					IconButton(onClick = { /* Acción de ayuda o notificaciones */ }) {
						Icon(
							painter = painterResource(id = R.drawable.ic_supports),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		},
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(16.dp)
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			
			// Logo
			Image(
				painter = painterResource(id = R.drawable.ic_logo), // ← tu logo aquí
				contentDescription = "Logo",
				modifier = Modifier
					.size(300.dp)
					.padding(bottom = 24.dp)
			)
			
			// Campo Email
			OutlinedTextField(
				value = email,
				onValueChange = { email = it },
				label = { Text("Correo electrónico") },
				singleLine = true,
				modifier = Modifier.fillMaxWidth()
			)
			
			Spacer(modifier = Modifier.height(12.dp))
			
			// Campo Contraseña
			OutlinedTextField(
				value = password,
				onValueChange = { password = it },
				label = { Text("Contraseña") },
				singleLine = true,
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
				modifier = Modifier.fillMaxWidth()
			)
			
			Spacer(modifier = Modifier.height(24.dp))
			
			// Botón de Login
			Button(
				onClick = {
					if (email.isNotBlank() && password.isNotBlank()) {
						isLoading = true
						// Lógica de autenticación aquí
					} else {
						errorMessage = "Completa todos los campos"
					}
				},
				modifier = Modifier
					.fillMaxWidth()
					.height(50.dp),
				enabled = !isLoading
			) {
				if (isLoading) {
					CircularProgressIndicator(
						color = MaterialTheme.colorScheme.onPrimary,
						modifier = Modifier.size(20.dp),
						strokeWidth = 2.dp
					)
				} else {
					Text("Iniciar sesión")
				}
			}
			
			// Mensaje de error
			errorMessage?.let {
				Spacer(modifier = Modifier.height(12.dp))
				Text(it, color = MaterialTheme.colorScheme.error)
			}
			
			Spacer(modifier = Modifier.height(24.dp))
			
			// Navegar al registro
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center
			) {
				Text("¿No tienes una cuenta? ")
				Text(
					text = "Regístrate",
					color = MaterialTheme.colorScheme.primary,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.clickable {
						navController.navigate(AppRoutes.RegisterScreen)
					}
				)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
	
	val navController = rememberNavController()
	LoginScreenContent(navController = navController)
}