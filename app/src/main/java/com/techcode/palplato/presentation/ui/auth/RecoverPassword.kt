package com.techcode.palplato.presentation.ui.auth

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar
import com.techcode.palplato.presentation.ui.order.Pedido
import com.techcode.palplato.presentation.ui.order.PedidoItem

@Composable
fun RecoverPasswordScreen(navController: NavController) {
		RecoverPasswordScreenContent(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecoverPasswordScreenContent(navController: NavController) {
	var email by rememberSaveable { mutableStateOf("") }
	var isLoading by rememberSaveable { mutableStateOf(false) }
	
	// Validación de email
	val isEmailValid = Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches()
	val emailError = email.isNotEmpty() && !isEmailValid
	
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
			// Ícono de recuperación
			Icon(
				imageVector = Icons.Default.Lock,
				contentDescription = null,
				modifier = Modifier
					.size(80.dp)
					.padding(bottom = 24.dp),
				tint = MaterialTheme.colorScheme.primary
			)
			
			// Título
			Text(
				text = "Recuperar Contraseña",
				style = MaterialTheme.typography.headlineMedium,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.padding(bottom = 8.dp)
			)
			
			// Subtítulo descriptivo
			Text(
				text = "Ingresa tu correo electrónico y te enviaremos un código de verificación para restablecer tu contraseña.",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center,
				modifier = Modifier.padding(bottom = 32.dp)
			)
			
			// Campo de correo electrónico
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
			
			// Botón para enviar código
			Button(
				onClick = {
					isLoading = true
					// Aquí iría la lógica para enviar el código
					// Después de enviar, podrías navegar a una pantalla de verificación
				},
				modifier = Modifier
					.fillMaxWidth()
					.height(50.dp),
				enabled = isEmailValid && !isLoading
			) {
				if (isLoading) {
					androidx.compose.material3.CircularProgressIndicator(
						color = MaterialTheme.colorScheme.onPrimary,
						modifier = Modifier.size(20.dp),
						strokeWidth = 2.dp
					)
				} else {
					Text("Enviar Código")
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			// Botón para regresar al login
			OutlinedButton(
				onClick = {
					navController.popBackStack() // Regresa a la pantalla anterior (login)
				},
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