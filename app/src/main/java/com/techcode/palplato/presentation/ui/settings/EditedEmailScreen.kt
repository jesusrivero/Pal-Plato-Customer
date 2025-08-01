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
import androidx.compose.runtime.LaunchedEffect
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
import com.techcode.palplato.domain.model.UserProfileUpdate
import com.techcode.palplato.domain.viewmodels.auth.UserViewModel
import com.techcode.palplato.utils.AppAlertDialog
import com.techcode.palplato.utils.AppConfirmDialog
import com.techcode.palplato.utils.Resource

@Composable
fun EditedEmailScreen(	navController: NavController){
	
	EditedEmailScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedEmailScreenContent(
	navController: NavController,
	viewModel: UserViewModel = hiltViewModel()
) {
	var newEmail by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }
	
	var emailError by remember { mutableStateOf<String?>(null) }
	var passwordError by remember { mutableStateOf<String?>(null) }
	var showConfirmDialog by remember { mutableStateOf(false) } // ✅ Estado para el diálogo
	
	val updateEmailState by viewModel.updateEmailState.collectAsState()
	val userProfileState by viewModel.userProfileState.collectAsState()
	
	// Cargar datos del perfil
	LaunchedEffect(Unit) {
		viewModel.getUserProfile()
	}
	
	LaunchedEffect(userProfileState) {
		if (userProfileState is Resource.Success) {
			val profile = (userProfileState as Resource.Success<UserProfileUpdate>).result
			newEmail = profile.email
		}
	}
	
	
	// Mostrar feedback
	AppAlertDialog(
		state = updateEmailState,
		onDismiss = {
			viewModel.resetUpdateEmailState()
			if (updateEmailState is Resource.Success) {
				navController.popBackStack()
			}
		}
	)
	
	// ✅ Diálogo de confirmación
	if (showConfirmDialog) {
		AppConfirmDialog(
			title = "Confirmar cambio de correo",
			message = "¿Estás seguro de que deseas cambiar tu correo a $newEmail?",
			onConfirm = {
				viewModel.updateUserEmail(newEmail, password)
			},
			onDismiss = { showConfirmDialog = false }
		)
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Correo electrónico", style = MaterialTheme.typography.titleMedium) },
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
			// Campo de nuevo correo
			OutlinedTextField(
				value = newEmail,
				onValueChange = {
					newEmail = it
					emailError = if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
						"Correo no válido"
					} else null
				},
				label = { Text("Nuevo correo") },
				isError = emailError != null,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				keyboardOptions = KeyboardOptions.Default.copy(
					keyboardType = KeyboardType.Email
				),
				modifier = Modifier.fillMaxWidth()
			)
			if (emailError != null) {
				Text(emailError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			Divider()
			
			// Campo de contraseña
			OutlinedTextField(
				value = password,
				onValueChange = {
					password = it
					passwordError = if (password.isBlank()) "La contraseña es obligatoria" else null
				},
				label = { Text("Contraseña actual") },
				isError = passwordError != null,
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				visualTransformation = PasswordVisualTransformation(),
				keyboardOptions = KeyboardOptions.Default.copy(
					keyboardType = KeyboardType.Password
				),
				modifier = Modifier.fillMaxWidth()
			)
			if (passwordError != null) {
				Text(passwordError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Botón Guardar cambios
			Button(
				onClick = {
					var isValid = true
					if (!android.util.Patterns.EMAIL_ADDRESS.matcher(newEmail).matches()) {
						emailError = "Correo no válido"
						isValid = false
					}
					if (password.isBlank()) {
						passwordError = "La contraseña es obligatoria"
						isValid = false
					}
					
					if (isValid) {
						showConfirmDialog = true // ✅ Mostrar diálogo antes de actualizar
					}
				},
				modifier = Modifier.fillMaxWidth(),
				colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
			) {
				if (updateEmailState is Resource.Loading) {
					CircularProgressIndicator(
						modifier = Modifier.size(20.dp),
						color = Color.Black
					)
				} else {
					Text("Guardar cambios", color = Color.Black)
				}
			}
		}
	}
}




@Preview(showBackground = true)
@Composable
fun EditedEmailScreenPreview() {
	
	val navController = rememberNavController()
	EditedEmailScreenContent(navController = navController)
}