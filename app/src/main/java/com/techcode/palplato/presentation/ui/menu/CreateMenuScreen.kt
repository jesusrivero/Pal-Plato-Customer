package com.techcode.palplato.presentation.ui.menu

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.viewmodels.auth.ProductViewModel
import com.techcode.palplato.utils.AppAlertDialog
import com.techcode.palplato.utils.Resource

@Composable
fun CreateMenuScreen(	navController: NavController){
	
	CreateMenuScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateMenuScreenContent(
	navController: NavController,
	viewModel: ProductViewModel = hiltViewModel(),
) {
	var dishName by rememberSaveable { mutableStateOf("") }
	var description by rememberSaveable { mutableStateOf("") }
	var price by rememberSaveable { mutableStateOf("") }
	var preparationTime by rememberSaveable { mutableStateOf("") }
	var selectedCategory by rememberSaveable { mutableStateOf("") }
	var size by rememberSaveable { mutableStateOf("") }
	var type by rememberSaveable { mutableStateOf("") }
	
	val categoryOptions = listOf("Entradas", "Plato fuerte", "Postres", "Bebidas")
	val sizeOptions = listOf("Pequeño", "Mediano", "Grande")
	val typeOptions = listOf("Fría", "Caliente", "Alcoholica", "Sin alcohol")
	
	var expanded by remember { mutableStateOf(false) }
	var expandedSize by remember { mutableStateOf(false) }
	var expandedType by remember { mutableStateOf(false) }
	
	// Imagen
	var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
	val imagePickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent()
	) { uri: Uri? -> selectedImageUri = uri }
	
	// Ingredientes principales
	var ingredienteInput by remember { mutableStateOf("") }
	val ingredientesPrincipales = remember { mutableStateListOf<String>() }
	
	// Errores
	var nameError by remember { mutableStateOf<String?>(null) }
	var priceError by remember { mutableStateOf<String?>(null) }
	var descriptionError by remember { mutableStateOf<String?>(null) }
	var categoryError by remember { mutableStateOf<String?>(null) }
	
	// Estado de creación
	val createProductState by viewModel.createProductState.collectAsState()
	
	// AlertDialog
	AppAlertDialog(
		state = createProductState,
		onDismiss = {
			viewModel.resetState()
			if (createProductState is Resource.Success) {
				navController.popBackStack()
			}
		}
	)
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Nuevo Menú", style = MaterialTheme.typography.titleMedium) },
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(16.dp)
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			// CATEGORÍA
			ExposedDropdownMenuBox(
				expanded = expanded,
				onExpandedChange = { expanded = !expanded }
			) {
				OutlinedTextField(
					value = selectedCategory,
					onValueChange = {},
					readOnly = true,
					label = { Text("Categoría") },
					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
					isError = categoryError != null,
					modifier = Modifier
						.fillMaxWidth()
						.menuAnchor(),
					shape = RoundedCornerShape(16.dp)
				)
				ExposedDropdownMenu(
					expanded = expanded,
					onDismissRequest = { expanded = false }
				) {
					categoryOptions.forEach { category ->
						DropdownMenuItem(
							text = { Text(category) },
							onClick = {
								selectedCategory = category
								categoryError = null
								expanded = false
							}
						)
					}
				}
			}
			if (categoryError != null) {
				Text(categoryError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// NOMBRE
			OutlinedTextField(
				value = dishName,
				onValueChange = {
					dishName = it
					nameError = if (dishName.isBlank()) "El nombre es obligatorio" else null
				},
				label = { Text("Nombre del producto") },
				isError = nameError != null,
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp)
			)
			if (nameError != null) {
				Text(nameError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// DESCRIPCIÓN
			OutlinedTextField(
				value = description,
				onValueChange = {
					description = it
					descriptionError = if (description.isBlank()) "La descripción es obligatoria" else null
				},
				label = { Text("Descripción") },
				isError = descriptionError != null,
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp),
				minLines = 3
			)
			if (descriptionError != null) {
				Text(descriptionError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// PRECIO
			OutlinedTextField(
				value = if (price.isNotEmpty()) "$price" else "",
				onValueChange = { input ->
					val cleanInput = input.replace("$", "").trim()
					price = cleanInput.filter { it.isDigit() || it == '.' }
					priceError = if (price.isBlank()) "El precio es obligatorio" else null
				},
				label = { Text("Precio") },
				leadingIcon = { Text("$") },
				isError = priceError != null,
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp)
			)
			if (priceError != null) {
				Text(priceError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			if (selectedCategory == "Bebidas") {
				// CAMPOS SOLO PARA BEBIDAS
				ExposedDropdownMenuBox(
					expanded = expandedSize,
					onExpandedChange = { expandedSize = !expandedSize }
				) {
					OutlinedTextField(
						value = size,
						onValueChange = {},
						readOnly = true,
						label = { Text("Tamaño") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedSize) },
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor(),
						shape = RoundedCornerShape(16.dp)
					)
					ExposedDropdownMenu(
						expanded = expandedSize,
						onDismissRequest = { expandedSize = false }
					) {
						sizeOptions.forEach { option ->
							DropdownMenuItem(
								text = { Text(option) },
								onClick = {
									size = option
									expandedSize = false
								}
							)
						}
					}
				}
				
				ExposedDropdownMenuBox(
					expanded = expandedType,
					onExpandedChange = { expandedType = !expandedType }
				) {
					OutlinedTextField(
						value = type,
						onValueChange = {},
						readOnly = true,
						label = { Text("Tipo de bebida") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedType) },
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor(),
						shape = RoundedCornerShape(16.dp)
					)
					ExposedDropdownMenu(
						expanded = expandedType,
						onDismissRequest = { expandedType = false }
					) {
						typeOptions.forEach { option ->
							DropdownMenuItem(
								text = { Text(option) },
								onClick = {
									type = option
									expandedType = false
								}
							)
						}
					}
				}
			} else {
				// CAMPOS SOLO PARA COMIDA
				OutlinedTextField(
					value = preparationTime,
					onValueChange = { preparationTime = it.filter { ch -> ch.isDigit() } },
					label = { Text("Tiempo de preparación (min)") },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					modifier = Modifier.fillMaxWidth(),
					shape = RoundedCornerShape(16.dp)
				)
				
				OutlinedTextField(
					value = ingredienteInput,
					onValueChange = { ingredienteInput = it },
					label = { Text("Ingrediente principal") },
					trailingIcon = {
						Icon(
							Icons.Default.Add,
							contentDescription = "Agregar",
							modifier = Modifier.clickable {
								if (ingredienteInput.isNotBlank()) {
									ingredientesPrincipales.add(ingredienteInput.trim())
									ingredienteInput = ""
								}
							}
						)
					},
					modifier = Modifier.fillMaxWidth(),
					shape = RoundedCornerShape(16.dp)
				)
				
				FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
					ingredientesPrincipales.forEach { ingrediente ->
						AssistChip(
							onClick = {},
							label = { Text(ingrediente, fontSize = 10.sp) },
							trailingIcon = {
								Icon(
									Icons.Default.Close,
									contentDescription = "Eliminar",
									modifier = Modifier.clickable { ingredientesPrincipales.remove(ingrediente) }
								)
							}
						)
					}
				}
			}
			
			// IMAGEN (AL FINAL)
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(180.dp)
					.clip(RoundedCornerShape(12.dp))
					.background(Color.LightGray)
					.clickable { imagePickerLauncher.launch("image/*") },
				contentAlignment = Alignment.Center
			) {
				if (selectedImageUri != null) {
					AsyncImage(
						model = selectedImageUri,
						contentDescription = "Imagen seleccionada",
						contentScale = ContentScale.Crop,
						modifier = Modifier.fillMaxSize()
					)
				} else {
					Icon(
						imageVector = Icons.Default.Image,
						contentDescription = "Agregar imagen",
						tint = Color.Gray,
						modifier = Modifier.size(48.dp)
					)
				}
			}
			
			// BOTÓN GUARDAR
			Button(
				onClick = {
					var isValid = true
					if (dishName.isBlank()) {
						nameError = "El nombre es obligatorio"
						isValid = false
					}
					if (price.isBlank()) {
						priceError = "El precio es obligatorio"
						isValid = false
					}
					if (description.isBlank()) {
						descriptionError = "La descripción es obligatoria"
						isValid = false
					}
					if (selectedCategory.isBlank()) {
						categoryError = "Debes seleccionar una categoría"
						isValid = false
					}
					
					if (isValid) {
						val product = Product(
							businessId = "",
							name = dishName,
							description = description,
							category = selectedCategory,
							imageUrl = selectedImageUri?.toString() ?: "",
							available = true,
							price = price.replace("$", "").trim().toDoubleOrNull() ?: 0.0,
							size = if (selectedCategory == "Bebidas") size else null,
							type = if (selectedCategory == "Bebidas") type else null
						)
						viewModel.createProduct(product, selectedImageUri)
					}
				},
				modifier = Modifier.fillMaxWidth(),
				colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
				shape = RoundedCornerShape(12.dp)
			) {
				Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black)
				Spacer(modifier = Modifier.width(8.dp))
				Text("Agregar al menú", color = Color.Black)
			}
		}
	}
}






@Preview(showBackground = true)
@Composable
fun CreateMenuScreenPreview() {
	
	val navController = rememberNavController()
	CreateMenuScreenContent(navController = navController)
}
