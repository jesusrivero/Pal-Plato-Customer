package com.techcode.palplato.presentation.ui.bussines

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditedschedulesBusseinessScreen(navController: NavController) {
	
	EditedschedulesBusseinessScreenContent(navController = navController)
	
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedschedulesBusseinessScreenContent(navController: NavController) {

	// Horarios
	val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
	val openingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	val closingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Editar horarios", style = MaterialTheme.typography.titleMedium) },
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
				.padding(horizontal = 24.dp, vertical = 16.dp)
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			
			val timeDialogState = rememberMaterialDialogState()
			var tempDay by remember { mutableStateOf<String?>(null) }
			var isStartTime by remember { mutableStateOf(true) }
			
			daysOfWeek.forEach { day ->
				val isOpen = remember { mutableStateOf(openingHours[day] != null) }
				val formatter = DateTimeFormatter.ofPattern("hh:mm a")
				val opening = openingHours[day]?.format(formatter) ?: "Cerrado"
				val closing = closingHours[day]?.format(formatter) ?: ""
				
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 8.dp),
					verticalAlignment = Alignment.CenterVertically,
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Column(modifier = Modifier.weight(1f)) {
						Text(
							text = day,
							style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
						)
						
						Text(
							text = if (!isOpen.value) "Cerrado" else "$opening - $closing",
							style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
						)
						
						Text(
							text = "Cambiar horario",
							style = MaterialTheme.typography.labelSmall.copy(
								color = colorScheme.primary,
								textDecoration = TextDecoration.Underline
							),
							modifier = Modifier
								.clickable(enabled = isOpen.value) {
									tempDay = day
									isStartTime = true
									timeDialogState.show()
								}
								.padding(top = 2.dp)
						)
					}
					
					Switch(
						checked = isOpen.value,
						colors = SwitchDefaults.colors(
							checkedThumbColor = colorScheme.primary,
							uncheckedThumbColor = colorScheme.outline,
							checkedTrackColor = colorScheme.primary.copy(alpha = 0.54f),
							uncheckedTrackColor = colorScheme.surfaceVariant
						),
						onCheckedChange = {
							isOpen.value = it
							if (!it) {
								openingHours[day] = null
								closingHours[day] = null
							} else {
								openingHours[day] = LocalTime.of(9, 0)
								closingHours[day] = LocalTime.of(18, 0)
							}
						}
					)
				}
			}
			
			Spacer(modifier = Modifier.height(24.dp))
			
			Button(
				onClick = {
					navController.navigate(AppRoutes.MainScreen)
				},
				modifier = Modifier
					.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp)
			) {
				Text("Guardar horarios", style = MaterialTheme.typography.labelLarge)
			}
			
			// MaterialDialog para seleccionar horas
			MaterialDialog(
				dialogState = timeDialogState,
				buttons = {
					positiveButton("Aceptar")
					negativeButton("Cancelar") {
						isStartTime = true
						tempDay = null
					}
				}
			) {
				timepicker(
					is24HourClock = false,
					title = if (isStartTime) "Selecciona hora de inicio" else "Selecciona hora de fin"
				) { time ->
					tempDay?.let { day ->
						if (isStartTime) {
							openingHours[day] = time
							isStartTime = false
							timeDialogState.show() // Vuelve a abrir para la hora de fin
						} else {
							closingHours[day] = time
							isStartTime = true
							tempDay = null
						}
					}
				}
			}
		}
		
	}
	EditedScheduleTimePicker(
		day = "Lunes",
		openingHours = openingHours,
		closingHours = closingHours
	)
	
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditedScheduleTimePicker(
	day: String,
	openingHours: SnapshotStateMap<String, LocalTime?>,
	closingHours: SnapshotStateMap<String, LocalTime?>
) {
	var isPickingStartTime by remember { mutableStateOf(true) }
//	var showDialog by remember { mutableStateOf(false) }
	
	val timeDialog = rememberMaterialDialogState()
	
	MaterialDialog(
		dialogState = timeDialog,
		buttons = {
			positiveButton("Aceptar")
			negativeButton("Cancelar")
		}
	) {
		timepicker(
			is24HourClock = true,
			title = if (isPickingStartTime) "Hora de inicio" else "Hora de cierre",
			initialTime = if (isPickingStartTime) {
				openingHours[day] ?: LocalTime.of(9, 0)
			} else {
				closingHours[day] ?: LocalTime.of(18, 0)
			}
		) { time ->
			if (isPickingStartTime) {
				openingHours[day] = time
				isPickingStartTime = false
				timeDialog.show() // abrir inmediatamente para hora fin
			} else {
				closingHours[day] = time
				isPickingStartTime = true // reinicia para el siguiente día
			}
		}
	}
	
	
	// Mostrar el horario actual (si ambos existen)
	if (openingHours[day] != null && closingHours[day] != null) {
		Text("Horario: ${openingHours[day]} - ${closingHours[day]}")
	}
}



@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun EditedschedulesBusseinessScreenPreview() {
	
	val navController = rememberNavController()
	EditedschedulesBusseinessScreenContent(navController = navController)
}