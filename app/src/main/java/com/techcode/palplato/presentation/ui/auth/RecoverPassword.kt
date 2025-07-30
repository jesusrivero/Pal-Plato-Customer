package com.techcode.palplato.presentation.ui.auth

import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.techcode.palplato.domain.viewmodels.auth.AuthViewModel
import com.techcode.palplato.utils.Resource

@Composable
fun RecoverPasswordScreen(navController: NavController) {
	RecoverPasswordScreenContent(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreenContent(
	navController: NavController,
	viewModel: AuthViewModel = hiltViewModel()
) {
	var email by rememberSaveable { mutableStateOf("") }
	val context = LocalContext.current
	
	val resetState by viewModel.resetPasswordState.collectAsState()
	var isLoading by rememberSaveable { mutableStateOf(false) }
	
	// Validación de email
	val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
	val emailError = email.isNotEmpty() && !isEmailValid
	
	// Escuchar cambios de estado de recuperación
	LaunchedEffect(resetState) {
		when (resetState) {
			is Resource.Loading -> isLoading = true
			is Resource.Success -> {
				isLoading = false
				Toast.makeText(context, "Correo de recuperación enviado", Toast.LENGTH_LONG).show()
				viewModel.clearResetPasswordState()
				navController.popBackStack() // Regresar al login
			}
			is Resource.Error -> {
				isLoading = false
				Toast.makeText(context, (resetState as Resource.Error).message, Toast.LENGTH_LONG).show()
				viewModel.clearResetPasswordState()
			}
			else -> Unit
		}
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						"Recuperar Contraseña",
						style = MaterialTheme.typography.titleMedium
					)
				}
			)
		}
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
			Icon(
				imageVector = Icons.Default.Lock,
				contentDescription = null,
				modifier = Modifier
					.size(80.dp)
					.padding(bottom = 24.dp),
				tint = MaterialTheme.colorScheme.primary
			)
			
			Text(
				text = "Recuperar Contraseña",
				style = MaterialTheme.typography.headlineMedium,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.padding(bottom = 8.dp)
			)
			
			Text(
				text = "Ingresa tu correo electrónico y te enviaremos un enlace para restablecer tu contraseña.",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(bottom = 32.dp)
			)
			
			OutlinedTextField(
				value = email,
				onValueChange = { email = it },
				label = { Text("Correo electrónico") },
				isError = emailError,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				modifier = Modifier.fillMaxWidth(),
				leadingIcon = {
					Icon(
						imageVector = Icons.Default.Email,
						contentDescription = null
					)
				}
			)
			
			if (emailError) {
				Text(
					text = "Correo electrónico inválido",
					color = MaterialTheme.colorScheme.error,
					style = MaterialTheme.typography.bodySmall,
					modifier = Modifier
						.align(Alignment.Start)
						.padding(top = 4.dp)
				)
			}
			
			Spacer(modifier = Modifier.height(32.dp))
			
			Button(
				onClick = {
					viewModel.resetPassword(email.trim())
				},
				modifier = Modifier
					.fillMaxWidth()
					.height(50.dp),
				enabled = isEmailValid && !isLoading
			) {
				if (isLoading) {
					CircularProgressIndicator(
						color = MaterialTheme.colorScheme.onPrimary,
						modifier = Modifier.size(20.dp),
						strokeWidth = 2.dp
					)
				} else {
					Text("Enviar correo")
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			OutlinedButton(
				onClick = { navController.popBackStack() },
				modifier = Modifier
					.fillMaxWidth()
					.height(50.dp)
			) {
				Text("Volver al Login")
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun RecoverPasswordScreenPreview() {


}