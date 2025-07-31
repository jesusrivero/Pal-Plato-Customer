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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
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
	val categoryOptions = listOf("Entradas", "Plato fuerte", "Postres", "Bebidas")
	var expanded by remember { mutableStateOf(false) }
	
	// Imagen
	var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
	val imagePickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent()
	) { uri: Uri? -> selectedImageUri = uri }
	
	// Ingredientes principales
	var ingredienteInput by remember { mutableStateOf("") }
	val ingredientesPrincipales = remember { mutableStateListOf<String>() }
	
	// Errores de validación
	var nameError by remember { mutableStateOf<String?>(null) }
	var timeError by remember { mutableStateOf<String?>(null) }
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
				},
				actions = {
					IconButton(onClick = { /* Notificaciones */ }) {
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
				.padding(16.dp)
				.verticalScroll(rememberScrollState()),
			verticalArrangement = Arrangement.spacedBy(12.dp)
		) {
			// Imagen
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
			
			// Nombre del plato
			OutlinedTextField(
				value = dishName,
				onValueChange = {
					dishName = it
					nameError = if (dishName.isBlank()) "El nombre es obligatorio" else null
				},
				label = { Text("Nombre del plato") },
				isError = nameError != null,
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp)
			)
			if (nameError != null) {
				Text(nameError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Ingredientes principales
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
			
			// Precio con formato automático corregido
			OutlinedTextField(
				value = if (price.isNotEmpty()) "$price" else "",
				onValueChange = { input ->
					val cleanInput = input.replace("$", "").trim()
					val filtered = cleanInput.filter { it.isDigit() || it == '.' }
					
					// Evitar más de un punto decimal
					val formatted = if (filtered.count { it == '.' } > 1) {
						filtered.dropLast(1)
					} else filtered
					
					price = formatted
					priceError = if (formatted.isEmpty()) "El precio es obligatorio" else null
				},
				label = { Text("Precio") },
				leadingIcon = { Text("$") }, // ✅ Mostrar $ fijo a la izquierda
				isError = priceError != null,
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp)
			)
			if (priceError != null) {
				Text(priceError!!, color = Color.Red, fontSize = 12.sp)
			}


// Tiempo de preparación con formato automático
			OutlinedTextField(
				value = preparationTime,
				onValueChange = { input ->
					val cleanInput = input.replace("min", "").trim()
					val filtered = cleanInput.filter { it.isDigit() }
					
					preparationTime = if (filtered.isNotEmpty()) {
						"$filtered min"
					} else ""
					timeError = if (filtered.isEmpty()) "El tiempo es obligatorio" else null
				},
				label = { Text("Tiempo de preparación") },
				isError = timeError != null,
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(16.dp)
			)
			if (timeError != null) {
				Text(timeError!!, color = Color.Red, fontSize = 12.sp)
			}
			
			// Descripción
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
			
			
			
			// Categoría
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
			
			// Botón de guardado
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
							preparationTime = preparationTime.replace("min", "").trim().toIntOrNull() ?: 0,
							ingredients = ingredientesPrincipales,
							available = true,
							price = price.replace("$", "").trim().toDoubleOrNull() ?: 0.0
						)
						viewModel.createProduct(product)
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
