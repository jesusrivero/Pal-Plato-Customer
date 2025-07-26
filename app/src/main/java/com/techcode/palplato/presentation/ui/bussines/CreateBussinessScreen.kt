package com.techcode.palplato.presentation.ui.bussines

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateBussinessScreen(navController: NavController) {
	
	CreateBussinessContent(navController = navController)
	
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBussinessContent(navController: NavController) {
	var step by rememberSaveable { mutableStateOf(1) }
	
	// Campos negocio
	var businessName by rememberSaveable { mutableStateOf("") }
	var businessPhone by rememberSaveable { mutableStateOf("") }
	var businessAddress by rememberSaveable { mutableStateOf("") }
	var businessDescription by rememberSaveable { mutableStateOf("") }
	
	// Categorías y productos
	val availableCategories = categoryProductList.map { it.name }
	fun getProductsForCategory(category: String): List<String> {
		return categoryProductList.find { it.name == category }?.products ?: emptyList()
	}
	
	var expanded by remember { mutableStateOf(false) }
	var productInput by remember { mutableStateOf("") }
	val selectedCategories = remember { mutableStateListOf<String>() }
	val productList = remember { mutableStateListOf<String>() }
	
	// Horarios
	val daysOfWeek = listOf("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo")
//	var showTimePicker by remember { mutableStateOf(false) }
//	var selectedDay by remember { mutableStateOf<String?>(null) }
//	var isPickingStartTime by remember { mutableStateOf(true) }
	val openingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	val closingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Crear negocio", style = MaterialTheme.typography.titleMedium) },
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
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 24.dp, vertical = 16.dp)
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(16.dp)
		) {
			
			when (step) {
				1 -> {
					
					
					OutlinedTextField(
						value = businessName,
						onValueChange = { businessName = it },
						label = { Text("Nombre del negocio") },
						leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
						singleLine = true,
						minLines = 1,
						shape = RoundedCornerShape(16.dp),
						modifier = Modifier.fillMaxWidth()
					)
					
					
					ExposedDropdownMenuBox(
						expanded = expanded,
						onExpandedChange = { expanded = !expanded }
					) {
						OutlinedTextField(
							readOnly = true,
							value = "",
							onValueChange = {},
							label = { Text("Selecciona hasta 3 categorías") },
							placeholder = {
								Text(
									text = if (selectedCategories.isEmpty()) "Selecciona" else selectedCategories.joinToString(),
									maxLines = 1
								)
							},
							trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
							modifier = Modifier
								.menuAnchor()
								.fillMaxWidth(),
							shape = RoundedCornerShape(16.dp)
						)
						
						ExposedDropdownMenu(
							expanded = expanded,
							onDismissRequest = { expanded = false },
							modifier = Modifier.background(colorScheme.surfaceVariant)
						) {
							availableCategories.forEach { category ->
								val isSelected = selectedCategories.contains(category)
								DropdownMenuItem(
									text = { Text(category) },
									onClick = {
										if (isSelected) {
											selectedCategories.remove(category)
											productList.removeAll(getProductsForCategory(category))
										} else if (selectedCategories.size < 3) {
											selectedCategories.add(category)
											productList.addAll(getProductsForCategory(category))
										}
										expanded = false
									}
								)
							}
						}
					}
					if (selectedCategories.isNotEmpty()) {
						FlowRow(
							horizontalArrangement = Arrangement.spacedBy(8.dp),
							verticalArrangement = Arrangement.spacedBy(8.dp)
						) {
							selectedCategories.forEach { category ->
								AssistChip(
									onClick = {},
									label = {
										Text(
											text = category,
											fontSize = 10.sp,
											maxLines = 1,
											modifier = Modifier.widthIn(min = 10.dp)
										)
									},
									trailingIcon = {
										Icon(
											imageVector = Icons.Default.Close,
											contentDescription = "Eliminar categoría",
											modifier = Modifier
												.size(12.dp)
												.clickable {
													selectedCategories.remove(category)
													productList.removeAll(getProductsForCategory(category))
												}
										)
									},
									modifier = Modifier
										.height(30.dp)
										.defaultMinSize(minWidth = 30.dp), // Uniforme
									shape = RoundedCornerShape(16.dp),
									colors = AssistChipDefaults.assistChipColors(
										containerColor =colorScheme.primaryContainer,
										labelColor = colorScheme.onPrimaryContainer
									)
								)
							}
						}
					}
					
					// Productos que ofrece
					OutlinedTextField(
						value = productInput,
						onValueChange = { productInput = it },
						label = { Text("Producto que ofrezco") },
						trailingIcon = {
							Icon(
								imageVector = Icons.Default.Add,
								contentDescription = "Agregar producto",
								modifier = Modifier
									.clickable {
										if (productInput.isNotBlank()) {
											productList.add(productInput.trim())
											productInput = ""
										}
									}
									.padding(4.dp)
							)
						},
						minLines = 1,
						shape = RoundedCornerShape(16.dp),
						modifier = Modifier.fillMaxWidth()
					)
					
					FlowRow(
						horizontalArrangement = Arrangement.spacedBy(8.dp),
						verticalArrangement = Arrangement.spacedBy(8.dp)
					) {
						productList.forEach { product ->
							AssistChip(
								onClick = {},
								label = {
									Text(
										text = product,
										fontSize = 10.sp,
										maxLines = 1,
										modifier = Modifier.widthIn(min = 10.dp)
									)
								},
								trailingIcon = {
									Icon(
										imageVector = Icons.Default.Close,
										contentDescription = "Eliminar producto",
										modifier = Modifier
											.size(12.dp)
											.clickable {
												productList.remove(product)
											}
									)
								},
								modifier = Modifier
									.height(30.dp)
									.defaultMinSize(minWidth = 30.dp), // Uniforme
								shape = RoundedCornerShape(16.dp),
								colors = AssistChipDefaults.assistChipColors(
									containerColor =colorScheme.primaryContainer,
									labelColor = colorScheme.onPrimaryContainer
								)
							)
						}
					}
					
					
					// Teléfono
					OutlinedTextField(
						value = businessPhone,
						onValueChange = { businessPhone = it },
						label = { Text("Teléfono") },
						leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
						singleLine = true,
						minLines = 1,
						keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
						shape = RoundedCornerShape(16.dp),
						modifier = Modifier.fillMaxWidth()
					)
					
					// Dirección
					OutlinedTextField(
						value = businessAddress,
						onValueChange = { businessAddress = it },
						label = { Text("Dirección") },
						leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
						trailingIcon = {
							Icon(
								imageVector = Icons.Default.Add,
								contentDescription = "Agregar dirección",
								modifier = Modifier
									.clickable {}
									.padding(4.dp)
							)
						},
						singleLine = false,
						minLines = 1,
						shape = RoundedCornerShape(16.dp),
						modifier = Modifier.fillMaxWidth()
					)
					
					// Descripcion
					OutlinedTextField(
						value = businessDescription,
						onValueChange = { businessDescription = it },
						label = { Text("Descripcion") },
						singleLine = false,
						minLines = 4,
						shape = RoundedCornerShape(16.dp),
						modifier = Modifier.fillMaxWidth()
					)
					
					// Logo del negocio
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
							.padding(24.dp),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						Text("Sube tu logo", fontWeight = FontWeight.Bold)
						Text(
							"El logo de tu negocio aparecerá en la app y en los pedidos de tus clientes.",
							style = MaterialTheme.typography.bodySmall,
							textAlign = TextAlign.Center,
							modifier = Modifier.padding(vertical = 8.dp)
						)
						Button(onClick = { /* Acción subir logo */ }) {
							Text("Subir logo")
						}
					}
					
					Spacer(modifier = Modifier.height(16.dp))
					
					Button(
						onClick = { step = 2 },
						modifier = Modifier
							.fillMaxWidth(),
						shape = RoundedCornerShape(16.dp)
					) {
						Text("Siguiente")
					}
				}
				
				2 -> {
					val timeDialogState = rememberMaterialDialogState()
					var tempDay by remember { mutableStateOf<String?>(null) }
					var isStartTime by remember { mutableStateOf(true) }
					
					Text(
						text = "Horarios del Negocio",
						style = MaterialTheme.typography.titleLarge,
						modifier = Modifier.padding(bottom = 4.dp)
					)
					
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
			ScheduleTimePicker(
				day = "Lunes",
				openingHours = openingHours,
				closingHours = closingHours
			)
			
		}
	}
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ScheduleTimePicker(
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




//@Preview(showBackground = true)
//@Composable
//fun SCreateBussinessPreview() {
//
//	val navController = rememberNavController()
//	CreateBussinessContent(navController = navController)
//}