package com.techcode.palplato.presentation.ui.auth

import android.util.Patterns
import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.techcode.palplato.R
import com.techcode.palplato.domain.viewmodels.auth.AuthViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.utils.Resource


@Composable
fun LoginScreen(navController: NavController){
	
	LoginScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
	navController: NavController,
  viewModel: AuthViewModel = hiltViewModel()
	) {
	var email by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }
	var isLoading by rememberSaveable { mutableStateOf(false) }
	// Estados de error
	val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
	val isPasswordValid = password.length >= 6
	val context = LocalContext.current
	val emailError = email.isNotEmpty() && !isEmailValid
  val passwordError = password.isNotEmpty() && !isPasswordValid
	var passwordVisible by rememberSaveable { mutableStateOf(false) }
	val formIsValid = isEmailValid && isPasswordValid
	val loginState by viewModel.loginState.collectAsState()
	
	LaunchedEffect(loginState) {
		when (loginState) {
			is Resource.Loading -> {
				isLoading = true
			}
			is Resource.Success -> {
				isLoading = false
				navController.navigate(AppRoutes.MainScreen) {
					popUpTo(AppRoutes.LoginScreen) { inclusive = true }
				}
			}
			is Resource.Error -> {
				isLoading = false
				(loginState as? Resource.Error)?.let { error ->
					Toast.makeText(context, error.message, Toast.LENGTH_LONG).show()
				}
				viewModel.resetLoginState()
			}
			else -> Unit
		}
	}

	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Iniciar Sesión", style = MaterialTheme.typography.titleMedium) },
				actions = {
					IconButton(onClick = { /* Acción de ayuda */ }) {
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
				.verticalScroll(rememberScrollState())
				.fillMaxSize(),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Image(
				painter = painterResource(id = R.drawable.ic_logo),
				contentDescription = "Logo",
				modifier = Modifier
					.size(300.dp)
					.padding(bottom = 24.dp)
			)
			
			// Correo electrónico
			OutlinedTextField(
				value = email,
				onValueChange = { email = it },
				label = { Text("Correo electrónico") },
				isError = emailError,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				modifier = Modifier.fillMaxWidth()
			)
			if (emailError) {
				Text(
					text = "Correo electrónico inválido",
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.bodySmall,
					modifier = Modifier.align(Alignment.Start)
				)
			}
			
			Spacer(modifier = Modifier.height(12.dp))
			
			// Contraseña
			OutlinedTextField(
				value = password,
				onValueChange = { password = it },
				label = { Text("Contraseña") },
				modifier = Modifier.fillMaxWidth(),
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
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
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
			)
			if (passwordError) {
				Text(
					text = "La contraseña debe tener al menos 6 caracteres",
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.bodySmall,
					modifier = Modifier.align(Alignment.Start)
				)
			}
			// Texto clicable para recuperación de contraseña
			Text(
				text = "¿Olvidaste tu contraseña?",
				color = MaterialTheme.colorScheme.primary,
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier
					.align(Alignment.End)
					.padding(top = 8.dp)
					.clickable {
						 navController.navigate(AppRoutes.RecoverPasswordScreen)
					}
			)
			
			Spacer(modifier = Modifier.height(24.dp))
			
			Button(
				onClick = {
					viewModel.login(email.trim(), password.trim())
//					isLoading = true
//					navController.navigate(AppRoutes.MainScreen)
				},
				modifier = Modifier
					.fillMaxWidth()
					.height(50.dp),
				enabled = formIsValid && !isLoading
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
			
			Spacer(modifier = Modifier.height(24.dp))
			
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.Center
			) {
				Text("¿No tienes una cuenta? ")
				Text(
					text = "Crear",
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

//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview() {
//
//	val navController = rememberNavController()
//	LoginScreenContent(navController = navController)
//}