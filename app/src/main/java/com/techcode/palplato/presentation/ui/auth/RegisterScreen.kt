package com.techcode.palplato.presentation.ui.auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun RegisterScreen(	navController: NavController){
	
	RegisterScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(navController: NavController) {
	var step by remember { mutableStateOf(1) }
	
	var name by remember { mutableStateOf("") }
	var lastName by remember { mutableStateOf("") }
	var cedula by remember { mutableStateOf("") }
	
	var email by remember { mutableStateOf("") }
	var password by remember { mutableStateOf("") }
	var acceptedTerms by remember { mutableStateOf(false) }
	
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
				.fillMaxWidth()
				.padding(16.dp)
				,horizontalAlignment = Alignment.CenterHorizontally
			,verticalArrangement = Arrangement.Center
		) {
			AnimatedContent(
				targetState = step,
				transitionSpec = {
					fadeIn(tween(300)) togetherWith fadeOut(tween(300))
				},
				label = "RegisterSteps"
			) { targetStep ->
				when (targetStep) {
					1 -> Column(
						verticalArrangement = Arrangement.spacedBy(16.dp),
						modifier = Modifier.fillMaxWidth()
					) {
						OutlinedTextField(
							value = name,
							onValueChange = { name = it },
							label = { Text("Nombre") },
							modifier = Modifier.fillMaxWidth()
						)
						OutlinedTextField(
							value = lastName,
							onValueChange = { lastName = it },
							label = { Text("Apellido") },
							modifier = Modifier.fillMaxWidth()
						)
						OutlinedTextField(
							value = cedula,
							onValueChange = { cedula = it },
							label = { Text("Cédula") },
							modifier = Modifier.fillMaxWidth()
						)
						Button(
							onClick = { step = 2 },
							modifier = Modifier.fillMaxWidth()
						) {
							Text("Siguiente")
						}
					}
					
					2 -> Column(
						verticalArrangement = Arrangement.spacedBy(16.dp),
						modifier = Modifier.fillMaxWidth()
					) {
						OutlinedTextField(
							value = email,
							onValueChange = { email = it },
							label = { Text("Correo electrónico") },
							modifier = Modifier.fillMaxWidth()
						)
						OutlinedTextField(
							value = password,
							onValueChange = { password = it },
							label = { Text("Contraseña") },
							visualTransformation = PasswordVisualTransformation(),
							modifier = Modifier.fillMaxWidth()
						)
						Row(
							verticalAlignment = Alignment.CenterVertically
						) {
							Checkbox(
								checked = acceptedTerms,
								onCheckedChange = { acceptedTerms = it }
							)
							Text(text = "Acepto los términos y condiciones")
						}
						Row(
							horizontalArrangement = Arrangement.spacedBy(8.dp),
							modifier = Modifier.fillMaxWidth()
						) {
							OutlinedButton(
								onClick = { step = 1 },
								modifier = Modifier.weight(1f)
							) {
								Text("Atrás")
							}
							Button(
								onClick = {
									// Acción de registrar (vacía por ahora)
								},
								modifier = Modifier.weight(1f)
							) {
								Text("Registrarse")
							}
						}
					}
				}
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
	
	val navController = rememberNavController()
	RegisterScreenContent(navController = navController)
}