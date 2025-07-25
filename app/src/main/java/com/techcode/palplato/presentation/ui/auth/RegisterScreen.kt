package com.techcode.palplato.presentation.ui.auth

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun RegisterScreen(navController: NavController) {
	
	RegisterScreenContent(navController = navController)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreenContent(navController: NavController) {
	var step by remember { mutableStateOf(1) }
	
	var name by remember { mutableStateOf("") }
	var lastName by remember { mutableStateOf("") }
	var email by remember { mutableStateOf("") }
	var password by remember { mutableStateOf("") }
	var confirmPassword by remember { mutableStateOf("") }
	var acceptedTerms by remember { mutableStateOf(false) }
	
	val uriHandler = LocalUriHandler.current
	
	// 🧠 Validaciones
	val namePattern = Regex("^[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]*$")
	val isNameValid = namePattern.matches(name.trim()) && name.trim().isNotEmpty()
	val isLastNameValid = namePattern.matches(lastName.trim()) && lastName.trim().isNotEmpty()
	val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
	val isPasswordValid = password.trim().length >= 6
	val doPasswordsMatch = password == confirmPassword
	val formIsValid = isNameValid && isLastNameValid && isEmailValid &&
			isPasswordValid && doPasswordsMatch && acceptedTerms
	var passwordVisible by rememberSaveable { mutableStateOf(false) }
	var confirmPasswordVisible by rememberSaveable { mutableStateOf(false) }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text(text = "", style = MaterialTheme.typography.titleMedium) },
				actions = {
					IconButton(onClick = { }) {
						Icon(
							painter = painterResource(id = R.drawable.ic_supports),
							contentDescription = "Soporte",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		}
	) { innerPadding ->
		Box(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			Column(
				modifier = Modifier
					.fillMaxWidth()
					.padding(16.dp),
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				// Nombre
				OutlinedTextField(
					value = name,
					onValueChange = {
						name = it.replaceFirstChar { c -> c.uppercaseChar() }
					},
					label = { Text("Nombre") },
					isError = name.isNotEmpty() && !isNameValid,
					shape = RoundedCornerShape(16.dp),
					modifier = Modifier.fillMaxWidth()
				)
				if (name.isNotEmpty() && !isNameValid) {
					Text("El nombre solo debe contener letras",
						color = MaterialTheme.colorScheme.error,
						style = MaterialTheme.typography.bodySmall,
						modifier = Modifier.align(Alignment.Start))
				}
				
				// Apellido
				OutlinedTextField(
					value = lastName,
					onValueChange = {
						lastName = it.replaceFirstChar { c -> c.uppercaseChar() }
					},
					label = { Text("Apellido") },
					isError = lastName.isNotEmpty() && !isLastNameValid,
					shape = RoundedCornerShape(16.dp),
					modifier = Modifier.fillMaxWidth()
				)
				if (lastName.isNotEmpty() && !isLastNameValid) {
					Text("El apellido solo debe contener letras",
						color = MaterialTheme.colorScheme.error,
						style = MaterialTheme.typography.bodySmall,
						modifier = Modifier.align(Alignment.Start))
				}
				
				// Correo
				OutlinedTextField(
					value = email,
					onValueChange = { email = it },
					label = { Text("Correo electrónico") },
					isError = email.isNotEmpty() && !isEmailValid,
					shape = RoundedCornerShape(16.dp),
					modifier = Modifier.fillMaxWidth()
				)
				if (email.isNotEmpty() && !isEmailValid) {
					Text("Correo electrónico inválido",
						color = MaterialTheme.colorScheme.error,
						style = MaterialTheme.typography.bodySmall,
						modifier = Modifier.align(Alignment.Start))
				}
				
				// Contraseña
				OutlinedTextField(
					value = password,
					onValueChange = { password = it },
					label = { Text("Contraseña") },
					shape = RoundedCornerShape(16.dp),
					singleLine = true,
					visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
					trailingIcon = {
						IconToggleButton(
							checked = passwordVisible,
							onCheckedChange = { passwordVisible = it }
						) {
							val visibilityIcon = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
							val description = if (passwordVisible) "Ocultar contraseña" else "Mostrar contraseña"
							
							Icon(imageVector = visibilityIcon, contentDescription = description)
						}
					},
					modifier = Modifier.fillMaxWidth(),
					keyboardOptions = KeyboardOptions(
						keyboardType = KeyboardType.Password,
						imeAction = ImeAction.Done
					)
				)
				
				// Confirmar contraseña
				OutlinedTextField(
					value = confirmPassword,
					onValueChange = { confirmPassword = it },
					label = { Text("Confirmar contraseña") },
					isError = confirmPassword.isNotEmpty() && !doPasswordsMatch,
					shape = RoundedCornerShape(16.dp),
					visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
					trailingIcon = {
						IconToggleButton(
							checked = confirmPasswordVisible,
							onCheckedChange = { confirmPasswordVisible = it }
						) {
							val visibilityIcon = if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff
							val description = if (confirmPasswordVisible) "Ocultar contraseña" else "Mostrar contraseña"
							
							Icon(imageVector = visibilityIcon, contentDescription = description)
						}
					},
					modifier = Modifier.fillMaxWidth()
				)
				if (confirmPassword.isNotEmpty() && !doPasswordsMatch) {
					Text("Las contraseñas no coinciden",
						color = MaterialTheme.colorScheme.error,
						style = MaterialTheme.typography.bodySmall,
						modifier = Modifier.align(Alignment.Start))
				}
				
				Spacer(modifier = Modifier.height(16.dp))
				
				// Términos
				Row(verticalAlignment = Alignment.CenterVertically) {
					Checkbox(
						checked = acceptedTerms,
						onCheckedChange = { acceptedTerms = it }
					)
					ClickableText(
						text = AnnotatedString("Acepto las políticas de privacidad"),
						onClick = {
							uriHandler.openUri("https://jesusrivero.github.io/condiciones-app/")
						},
						style = MaterialTheme.typography.bodySmall.copy(
							fontSize = 14.sp,
							color = MaterialTheme.colorScheme.primary,
							textDecoration = TextDecoration.Underline
						),
						modifier = Modifier.padding(start = 4.dp)
					)
				}
				
				Spacer(modifier = Modifier.height(16.dp))
				
				// Botón Crear Cuenta
				Button(
					onClick = {
						navController.navigate(AppRoutes.CreateBussinessScreen)
					},
					modifier = Modifier.fillMaxWidth(),
					enabled = formIsValid
				) {
					Text("Crear Cuenta")
				}
				
				Spacer(modifier = Modifier.height(16.dp))
				
				Text(
					text = "¿Ya tienes una cuenta?",
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					modifier = Modifier.align(Alignment.CenterHorizontally)
				)
				
				Spacer(modifier = Modifier.height(8.dp))
				
				Text(
					text = "Iniciar sesión",
					style = MaterialTheme.typography.labelLarge,
					color = MaterialTheme.colorScheme.primary,
					modifier = Modifier
						.align(Alignment.CenterHorizontally)
						.clickable { navController.popBackStack() }
				)
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