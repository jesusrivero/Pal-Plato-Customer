package com.techcode.palplato.presentation.ui.bussines

import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudUpload
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.techcode.palplato.R
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.model.BusinessSchedule
import com.techcode.palplato.domain.model.Category
import com.techcode.palplato.domain.viewmodels.auth.BusinessViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.utils.Resource
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
	val openingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	val closingHours = remember { mutableStateMapOf<String, LocalTime?>() }
	val viewModel: BusinessViewModel = hiltViewModel()
	val businessState by viewModel.businessState.collectAsState()
	val context = LocalContext.current
	// Logo del negocio
	var logoUri by remember { mutableStateOf<Uri?>(null) }
	val imagePickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent()
	) { uri: Uri? ->
		logoUri = uri
	}
	
	LaunchedEffect(businessState) {
		when (businessState) {
			is Resource.Success -> {
				Toast.makeText(context, "Negocio creado correctamente", Toast.LENGTH_LONG).show()
				navController.navigate(AppRoutes.MainScreen)
				viewModel.resetState()
			}
			is Resource.Error -> {
				Toast.makeText(context, (businessState as Resource.Error).message, Toast.LENGTH_LONG).show()
			}
			else -> Unit
		}
	}
	
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
					
					// Logo del negocio - Diseño profesional
					Column(
						modifier = Modifier
							.fillMaxWidth()
							.background(
								Brush.verticalGradient(
									colors = listOf(
										Color(0xFFF9FAFB),
										Color(0xFFF3F4F6)
									)
								),
								RoundedCornerShape(20.dp)
							)
							.border(
								1.5.dp,
								Brush.linearGradient(
									colors = listOf(
										Color(0xFF2563EB),
										Color(0xFF1E40AF)
									)
								),
								RoundedCornerShape(20.dp)
							)
							.padding(24.dp),
						horizontalAlignment = Alignment.CenterHorizontally
					) {
						// Título
						Row(
							verticalAlignment = Alignment.CenterVertically
						) {
							Icon(
								Icons.Default.PhotoCamera,
								contentDescription = null,
								tint = Color(0xFF2563EB),
								modifier = Modifier.size(26.dp)
							)
							Spacer(Modifier.width(8.dp))
							Text(
								"Sube tu logo",
								fontWeight = FontWeight.Bold,
								fontSize = 20.sp,
								color = Color(0xFF111827)
							)
						}
						
						Spacer(Modifier.height(6.dp))
						
						Text(
							"Personaliza tu negocio con una imagen profesional",
							fontSize = 14.sp,
							color = Color(0xFF6B7280),
							textAlign = TextAlign.Center
						)
						
						Spacer(Modifier.height(20.dp))
						
						// Contenedor de imagen con estilo moderno
						Box(
							modifier = Modifier
								.size(260.dp)
								.clip(RoundedCornerShape(24.dp))
								.background(Color.White)
								.border(
									1.5.dp,
									Brush.linearGradient(
										colors = listOf(
											Color(0xFFE5E7EB),
											Color(0xFFD1D5DB)
										)
									),
									RoundedCornerShape(24.dp)
								)
								.clickable { imagePickerLauncher.launch("image/*") },
							contentAlignment = Alignment.Center
						) {
							if (logoUri != null) {
								AsyncImage(
									model = logoUri,
									contentDescription = "Logo del negocio",
									contentScale = ContentScale.Crop,
									modifier = Modifier.fillMaxSize()
								)
								
								// Overlay de edición con degradado
								Box(
									modifier = Modifier
										.fillMaxSize()
										.background(Color.Black.copy(alpha = 0.35f)),
									contentAlignment = Alignment.Center
								) {
									Row(
										verticalAlignment = Alignment.CenterVertically
									) {
										Icon(
											Icons.Default.Edit,
											contentDescription = "Editar",
											tint = Color.White,
											modifier = Modifier.size(28.dp)
										)
										Spacer(Modifier.width(6.dp))
										Text(
											"Cambiar imagen",
											color = Color.White,
											fontWeight = FontWeight.Medium,
											fontSize = 14.sp
										)
									}
								}
							} else {
								Column(horizontalAlignment = Alignment.CenterHorizontally) {
									// Icono circular con degradado
									Box(
										modifier = Modifier
											.size(80.dp)
											.background(
												Brush.linearGradient(
													colors = listOf(
														Color(0xFF2563EB),
														Color(0xFF1E40AF)
													)
												),
												CircleShape
											),
										contentAlignment = Alignment.Center
									) {
										Icon(
											Icons.Default.CloudUpload,
											contentDescription = "Subir imagen",
											tint = Color.White,
											modifier = Modifier.size(36.dp)
										)
									}
									
									Spacer(Modifier.height(12.dp))
									
									Text(
										"Toca para subir",
										fontWeight = FontWeight.SemiBold,
										fontSize = 16.sp,
										color = Color(0xFF374151)
									)
									
									Spacer(Modifier.height(4.dp))
									
									Text(
										"JPG, PNG o GIF • Máx. 15MB",
										fontSize = 12.sp,
										color = Color(0xFF6B7280)
									)
								}
							}
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
							val categories = selectedCategories.map { category ->
								Category(
									name = category,
									products = productList.filter { product ->
										categoryProductList.find { it.name == category }?.products?.contains(product) == true
									}
								)
							}
							
							val formatter = DateTimeFormatter.ofPattern("HH:mm")
							val schedule = daysOfWeek.map { day ->
								BusinessSchedule(
									day = day,
									openTime = openingHours[day]?.format(formatter),
									closeTime = closingHours[day]?.format(formatter),
									isOpen = openingHours[day] != null && closingHours[day] != null
								)
							}
							
							val business = Business(
								ownerId = FirebaseAuth.getInstance().currentUser?.uid ?: "",
								name = businessName,
								direction = businessAddress,
								phone = businessPhone,
								description = businessDescription,
								logoUrl = null,
								categories = categories,
								schedule = schedule // 👈 Se guarda el horario completo
							)
							
							viewModel.createBusiness(business)
						},
						modifier = Modifier.fillMaxWidth(),
						shape = RoundedCornerShape(16.dp)
					) {
						Text("Guardar negocio")
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