package com.techcode.palplato.presentation.ui.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.domain.viewmodels.auth.UserViewModel
import com.techcode.palplato.utils.AppAlertDialog
import com.techcode.palplato.utils.AppConfirmDialog
import com.techcode.palplato.utils.Resource

@Composable
fun EditedPasswordScreen(	navController: NavController){
	
	EditedPasswordScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedPasswordScreenContent(
	navController: NavController,
	viewModel: UserViewModel = hiltViewModel()
) {
	var newPassword by rememberSaveable { mutableStateOf("") }
	var confirmPassword by rememberSaveable { mutableStateOf("") }
	var currentPassword by rememberSaveable { mutableStateOf("") }
	
	var newPasswordError by remember { mutableStateOf<String?>(null) }
	var confirmPasswordError by remember { mutableStateOf<String?>(null) }
	var currentPasswordError by remember { mutableStateOf<String?>(null) }
	
	var showConfirmDialog by remember { mutableStateOf(false) }
	
	val updatePasswordState by viewModel.updatePasswordState.collectAsState()
	
	// ✅ Dialogo de resultado
	AppAlertDialog(
		state = updatePasswordState,
		onDismiss = {
			viewModel.resetUpdatePasswordState()
			if (updatePasswordState is Resource.Success) {
				navController.popBackStack()
			}
		}
	)
	
	// ✅ Dialogo de confirmación
	if (showConfirmDialog) {
		AppConfirmDialog(
			title = "Confirmar cambio",
			message = "¿Estás seguro de que deseas actualizar tu contraseña?",
			onConfirm = {
				viewModel.updateUserPassword(currentPassword, newPassword)
			},
			onDismiss = { showConfirmDialog = false }
		)
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Contraseña", style = MaterialTheme.typography.titleMedium) },
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
			
			// Nueva contraseña
			OutlinedTextField(
				value = newPassword,
				onValueChange = {
					newPassword = it
					newPasswordError = if (newPassword.length < 6) "Debe tener al menos 6 caracteres" else null
				},
				label = { Text("Nueva contraseña") },
				isError = newPasswordError != null,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
				modifier = Modifier.fillMaxWidth()
			)
			if (newPasswordError != null) {
				Text(newPasswordError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Confirmar contraseña
			OutlinedTextField(
				value = confirmPassword,
				onValueChange = {
					confirmPassword = it
					confirmPasswordError = if (confirmPassword != newPassword) "Las contraseñas no coinciden" else null
				},
				label = { Text("Confirmar contraseña") },
				isError = confirmPasswordError != null,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
				modifier = Modifier.fillMaxWidth()
			)
			if (confirmPasswordError != null) {
				Text(confirmPasswordError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			Divider()
			
			// Contraseña actual
			OutlinedTextField(
				value = currentPassword,
				onValueChange = {
					currentPassword = it
					currentPasswordError = if (currentPassword.isBlank()) "La contraseña actual es obligatoria" else null
				},
				label = { Text("Contraseña actual") },
				isError = currentPasswordError != null,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
				modifier = Modifier.fillMaxWidth()
			)
			if (currentPasswordError != null) {
				Text(currentPasswordError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Botón guardar
			Button(
				onClick = {
					var isValid = true
					
					if (newPassword.length < 6) {
						newPasswordError = "Debe tener al menos 6 caracteres"
						isValid = false
					}
					if (confirmPassword != newPassword) {
						confirmPasswordError = "Las contraseñas no coinciden"
						isValid = false
					}
					if (currentPassword.isBlank()) {
						currentPasswordError = "La contraseña actual es obligatoria"
						isValid = false
					}
					
					if (isValid) {
						showConfirmDialog = true
					}
				},
				modifier = Modifier.fillMaxWidth(),
				colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
			) {
				if (updatePasswordState is Resource.Loading) {
					CircularProgressIndicator(modifier = Modifier.size(20.dp), color = Color.Black)
				} else {
					Text("Guardar cambios", color = Color.Black)
				}
			}
		}
	}
}




@Preview(showBackground = true)
@Composable
fun EditedPasswordScreenPreview() {
	
	val navController = rememberNavController()
	EditedPasswordScreenContent(navController = navController)
}