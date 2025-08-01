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
fun EditedNameScreen(	navController: NavController){
	
	EditedNameScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedNameScreenContent(
	navController: NavController,
	viewModel: UserViewModel = hiltViewModel()
) {
	var firstName by rememberSaveable { mutableStateOf("") }
	var lastName by rememberSaveable { mutableStateOf("") }
	var email by rememberSaveable { mutableStateOf("") }
	var password by rememberSaveable { mutableStateOf("") }
	
	var firstNameError by remember { mutableStateOf<String?>(null) }
	var lastNameError by remember { mutableStateOf<String?>(null) }
	var passwordError by remember { mutableStateOf<String?>(null) }
	
	val updateProfileState by viewModel.updateProfileState.collectAsState()
	val userProfileState by viewModel.userProfileState.collectAsState()
	
	// Estado del diálogo de confirmación
	var showConfirmDialog by remember { mutableStateOf(false) }
	
	LaunchedEffect(Unit) {
		viewModel.getUserProfile()
	}
	
	LaunchedEffect(userProfileState) {
		if (userProfileState is Resource.Success) {
			val profile = (userProfileState as Resource.Success<UserProfileUpdate>).result
			firstName = profile.name
			lastName = profile.lastname
			email = profile.email
		}
	}
	
	// Feedback de actualización
	AppAlertDialog(
		state = updateProfileState,
		onDismiss = {
			viewModel.resetUpdateProfileState()
			if (updateProfileState is Resource.Success) {
				navController.popBackStack()
			}
		}
	)
	
	// Diálogo de confirmación
	if (showConfirmDialog) {
		AppConfirmDialog(
			title = "Confirmar cambios",
			message = "¿Estás seguro de actualizar tu nombre y apellido?",
			onConfirm = {
				viewModel.updateUserProfile(firstName, lastName, email, password)
				showConfirmDialog = false
			},
			onDismiss = { showConfirmDialog = false }
		)
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Nombre completo", style = MaterialTheme.typography.titleMedium) },
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
			// Campo de nombres
			OutlinedTextField(
				value = firstName,
				onValueChange = {
					firstName = it
					firstNameError = if (firstName.isBlank()) "El nombre es obligatorio" else null
				},
				label = { Text("Nombres") },
				isError = firstNameError != null,
				shape = RoundedCornerShape(16.dp),
				singleLine = true,
				modifier = Modifier.fillMaxWidth()
			)
			if (firstNameError != null) {
				Text(firstNameError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Campo de apellidos
			OutlinedTextField(
				value = lastName,
				onValueChange = {
					lastName = it
					lastNameError = if (lastName.isBlank()) "El apellido es obligatorio" else null
				},
				label = { Text("Apellidos") },
				isError = lastNameError != null,
				shape = RoundedCornerShape(16.dp),
				singleLine = true,
				modifier = Modifier.fillMaxWidth()
			)
			if (lastNameError != null) {
				Text(lastNameError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			Divider()
			
			// Campo de contraseña actual
			OutlinedTextField(
				value = password,
				onValueChange = {
					password = it
					passwordError = if (password.isBlank()) "La contraseña es obligatoria" else null
				},
				label = { Text("Contraseña actual") },
				isError = passwordError != null,
				shape = RoundedCornerShape(16.dp),
				singleLine = true,
				visualTransformation = PasswordVisualTransformation(),
				modifier = Modifier.fillMaxWidth()
			)
			if (passwordError != null) {
				Text(passwordError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Botón guardar cambios
			Button(
				onClick = {
					var isValid = true
					if (firstName.isBlank()) {
						firstNameError = "El nombre es obligatorio"
						isValid = false
					}
					if (lastName.isBlank()) {
						lastNameError = "El apellido es obligatorio"
						isValid = false
					}
					if (password.isBlank()) {
						passwordError = "La contraseña es obligatoria"
						isValid = false
					}
					
					if (isValid) {
						showConfirmDialog = true
					}
				},
				modifier = Modifier.fillMaxWidth(),
				colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
			) {
				if (updateProfileState is Resource.Loading) {
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
fun EditedNameScreenPreview() {
	
	val navController = rememberNavController()
	EditedNameScreenContent(navController = navController)
}