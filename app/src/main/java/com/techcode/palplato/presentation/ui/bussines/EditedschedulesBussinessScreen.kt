package com.techcode.palplato.presentation.ui.bussines

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.domain.model.BusinessSchedule
import com.techcode.palplato.domain.viewmodels.auth.BusinessViewModel
import com.techcode.palplato.utils.AppAlertDialog
import com.techcode.palplato.utils.AppConfirmDialog
import com.techcode.palplato.utils.Resource
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
	val viewModel: BusinessViewModel = hiltViewModel()
	val businessData by viewModel.businessData.collectAsState()
	
	val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
	val openingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	val closingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	val switchStates = remember { mutableStateMapOf<String, Boolean>() }
	val businessState by viewModel.businessState.collectAsState()
	val formatter = DateTimeFormatter.ofPattern("HH:mm")
	var showConfirmDialog by remember { mutableStateOf(false) }
	val isActive = businessData?.state ?: true
	
	AppAlertDialog(
		state = businessState,
		onDismiss = {
			viewModel.resetState()
			if (businessState is Resource.Success) {
				navController.popBackStack()
			}
		}
	)
	
	if (showConfirmDialog) {
		AppConfirmDialog(
			title = "Confirmar cambios",
			message = "¿Estás seguro de que deseas guardar los horarios actualizados?",
			confirmText = "Guardar",
			cancelText = "Cancelar",
			onConfirm = {
				val updatedSchedule = daysOfWeek.map { day ->
					BusinessSchedule(
						day = day,
						openTime = openingHours[day]?.format(formatter),
						closeTime = closingHours[day]?.format(formatter),
						isOpen = switchStates[day] == true
					)
				}
				
				val updates = mapOf(
					"schedule" to updatedSchedule.map {
						mapOf(
							"day" to it.day,
							"openTime" to it.openTime,
							"closeTime" to it.closeTime,
							"isOpen" to it.isOpen
						)
					}
				)
				
				viewModel.updateBusiness(updates)
			},
			onDismiss = { showConfirmDialog = false }
		)
	}
	
	
	// Cargar datos del negocio
	LaunchedEffect(Unit) {
		viewModel.getBusinessData()
	}
	
	// Inicializar horarios y switches solo cuando llegan datos nuevos
	LaunchedEffect(businessData) {
		Log.d("EditedSchedulesScreen", "BusinessData recibido: $businessData")
		businessData?.schedule?.forEach { schedule ->
			Log.d(
				"EditedSchedulesScreen",
				"Día: ${schedule.day}, isOpen: ${schedule.isOpen}, openTime: ${schedule.openTime}, closeTime: ${schedule.closeTime}"
			)
			switchStates[schedule.day] = schedule.isOpen
			if (schedule.isOpen) {
				openingHours[schedule.day] = schedule.openTime?.let { LocalTime.parse(it, formatter) }
				closingHours[schedule.day] = schedule.closeTime?.let { LocalTime.parse(it, formatter) }
			} else {
				openingHours[schedule.day] = null
				closingHours[schedule.day] = null
			}
		}
	}
	
	val timeDialogState = rememberMaterialDialogState()
	var tempDay by remember { mutableStateOf<String?>(null) }
	var isStartTime by remember { mutableStateOf(true) }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Editar horarios", style = MaterialTheme.typography.titleMedium) },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
					}
				}
			)
		}
	) { innerPadding ->
		
		if (businessData == null) {
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				CircularProgressIndicator()
			}
		} else {
			// 👇 Forzar recomposición al cambiar businessData
			key(businessData) {
				Column(
					modifier = Modifier
						.padding(innerPadding)
						.padding(horizontal = 24.dp, vertical = 16.dp)
						.verticalScroll(rememberScrollState()),
					verticalArrangement = Arrangement.spacedBy(16.dp)
				) {
					daysOfWeek.forEach { day ->
						val isOpen = switchStates[day] ?: false
						val opening = openingHours[day]?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: "Cerrado"
						val closing = closingHours[day]?.format(DateTimeFormatter.ofPattern("hh:mm a")) ?: ""
						
						Row(
							modifier = Modifier
								.fillMaxWidth()
								.padding(vertical = 8.dp),
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.SpaceBetween
						) {
							Column(modifier = Modifier.weight(1f)) {
								Text(day, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold))
								Text(
									text = if (!isOpen) "Cerrado" else "$opening - $closing",
									style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
								)
								Text(
									text = "Cambiar horario",
									style = MaterialTheme.typography.labelSmall.copy(
										color = MaterialTheme.colorScheme.primary,
										textDecoration = TextDecoration.Underline
									),
									modifier = Modifier
										.clickable(enabled = isOpen) {
											tempDay = day
											isStartTime = true
											timeDialogState.show()
										}
										.padding(top = 2.dp)
								)
							}
							
							Switch(
								checked = isOpen,
								colors = SwitchDefaults.colors(
									checkedThumbColor = colorScheme.primary,
									uncheckedThumbColor = colorScheme.outline,
									checkedTrackColor = colorScheme.primary.copy(alpha = 0.54f),
									uncheckedTrackColor = colorScheme.surfaceVariant
								),
								onCheckedChange = { checked ->
									switchStates[day] = checked
									if (!checked) {
										openingHours[day] = null
										closingHours[day] = null
									} else {
										if (openingHours[day] == null) openingHours[day] = LocalTime.of(9, 0)
										if (closingHours[day] == null) closingHours[day] = LocalTime.of(18, 0)
									}
								}
							)
						}
					}
					
					Spacer(modifier = Modifier.height(24.dp))
					
					Button(
						onClick = { showConfirmDialog = true },
						modifier = Modifier.fillMaxWidth(),
						shape = RoundedCornerShape(16.dp)
					) {
						Text("Guardar horarios", style = MaterialTheme.typography.labelLarge)
					}
					
					
					
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
									timeDialogState.show()
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
		}
	}
}






@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun EditedScheduleTimePicker(
	day: String,
	openingHours: SnapshotStateMap<String, LocalTime?>,
	closingHours: SnapshotStateMap<String, LocalTime?>
) {
	var isPickingStartTime by remember { mutableStateOf(true) }
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