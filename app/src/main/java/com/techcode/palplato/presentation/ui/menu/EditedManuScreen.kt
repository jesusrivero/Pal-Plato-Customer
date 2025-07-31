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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.viewmodels.auth.ProductViewModel
import com.techcode.palplato.utils.AppAlertDialog
import com.techcode.palplato.utils.Resource

@Composable
fun EditedMenuScreen(
	navController: NavController,
	productId: String,
	viewModel: ProductViewModel = hiltViewModel()
) {
	LaunchedEffect(productId) {
		viewModel.getProduct(productId)
	}
	
	val selectedProduct by viewModel.selectedProduct.collectAsState()
	
	EditedMenuScreenContent(
		navController = navController,
		selectedProduct = selectedProduct
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedMenuScreenContent(
	navController: NavController,
	selectedProduct: Product?,
	viewModel: ProductViewModel = hiltViewModel()
) {
	var dishName by rememberSaveable { mutableStateOf("") }
	var description by rememberSaveable { mutableStateOf("") }
	var price by rememberSaveable { mutableStateOf("") }
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
	
	// Ingredientes
	var ingredienteInput by remember { mutableStateOf("") }
	val ingredientesPrincipales = remember { mutableStateListOf<String>() }
	
	// Estado de actualización
	val updateState by viewModel.updateState.collectAsState()
	
	// Autocompletar datos
	LaunchedEffect(selectedProduct) {
		selectedProduct?.let {
			dishName = it.name
			description = it.description
			price = it.price.toString()
			selectedCategory = it.category
			size = it.size ?: ""
			type = it.type ?: ""
			ingredientesPrincipales.clear()
			ingredientesPrincipales.addAll(it.ingredients)
			selectedImageUri = it.imageUrl.takeIf { url -> url.isNotBlank() }?.let { Uri.parse(it) }
		}
	}
	
	// Alert de feedback
	AppAlertDialog(
		state = updateState,
		onDismiss = {
			viewModel.resetUpdateState()
			if (updateState is Resource.Success) {
				navController.popBackStack()
			}
		}
	)
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Editar menú", style = MaterialTheme.typography.titleMedium) },
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
			// CATEGORÍA primero
			ExposedDropdownMenuBox(
				expanded = expanded,
				
				onExpandedChange = { expanded = !expanded }
			) {
				OutlinedTextField(
					value = selectedCategory,
					onValueChange = {},
					readOnly = true,
					shape = RoundedCornerShape(16.dp),
					label = { Text("Categoría") },
					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
					modifier = Modifier
						.fillMaxWidth()
						.menuAnchor()
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
								expanded = false
							}
						)
					}
				}
			}
			
			// Nombre
			OutlinedTextField(
				value = dishName,
				shape = RoundedCornerShape(16.dp),
				onValueChange = { dishName = it },
				label = { Text("Nombre del producto") },
				modifier = Modifier.fillMaxWidth()
			)
			
			// Precio
			OutlinedTextField(
				value = price,
				onValueChange = { price = it },
				label = { Text("Precio") },
				shape = RoundedCornerShape(16.dp),
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth()
			)
			
			// Descripción
			OutlinedTextField(
				value = description,
				onValueChange = { description = it },
				label = { Text("Descripción") },
				shape = RoundedCornerShape(16.dp),
				modifier = Modifier
					.fillMaxWidth()
					.height(100.dp)
			)
			
			// Si es bebida mostramos tamaño y tipo
			if (selectedCategory == "Bebidas") {
				ExposedDropdownMenuBox(
					expanded = expandedSize,
					onExpandedChange = { expandedSize = !expandedSize }
				) {
					OutlinedTextField(
						value = size,
						onValueChange = {},
						readOnly = true,
						shape = RoundedCornerShape(16.dp),
						label = { Text("Tamaño") },
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedSize) },
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor()
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
						shape = RoundedCornerShape(16.dp),
						trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expandedType) },
						modifier = Modifier
							.fillMaxWidth()
							.menuAnchor()
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
				// Si no es bebida mostramos ingredientes
				OutlinedTextField(
					value = ingredienteInput,
					onValueChange = { ingredienteInput = it },
					label = { Text("Ingrediente principal") },
					shape = RoundedCornerShape(16.dp),
					trailingIcon = {
						Icon(
							imageVector = Icons.Default.Add,
							contentDescription = "Agregar ingrediente",
							modifier = Modifier.clickable {
								if (ingredienteInput.isNotBlank()) {
									ingredientesPrincipales.add(ingredienteInput.trim())
									ingredienteInput = ""
								}
							}
						)
					},
					modifier = Modifier.fillMaxWidth()
				)
				
				FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
					ingredientesPrincipales.forEach { ingrediente ->
						AssistChip(
							onClick = {},
							label = { Text(ingrediente) },
							trailingIcon = {
								Icon(
									imageVector = Icons.Default.Close,
									contentDescription = "Eliminar",
									modifier = Modifier.clickable {
										ingredientesPrincipales.remove(ingrediente)
									}
								)
							}
						)
					}
				}
			}
			
			// Imagen al final
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
			
			// Botón Guardar
			Button(
				onClick = {
					selectedProduct?.let {
						val updatedProduct = it.copy(
							name = dishName,
							description = description,
							price = price.toDoubleOrNull() ?: 0.0,
							category = selectedCategory,
							size = if (selectedCategory == "Bebidas") size else null,
							type = if (selectedCategory == "Bebidas") type else null,
							ingredients = if (selectedCategory != "Bebidas") ingredientesPrincipales else emptyList(),
							imageUrl = selectedImageUri?.toString() ?: it.imageUrl
						)
						viewModel.updateProduct(updatedProduct)
					}
				},
				modifier = Modifier.fillMaxWidth(),
				colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
			) {
				if (updateState is Resource.Loading) {
					CircularProgressIndicator(
						modifier = Modifier.size(24.dp),
						color = Color.Black
					)
				} else {
					Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black)
					Spacer(modifier = Modifier.width(8.dp))
					Text("Guardar cambios", color = Color.Black)
				}
			}
		}
	}
}




//@Preview(showBackground = true)
//@Composable
//fun EditedMenuScreenPreview() {
//
//	val navController = rememberNavController()
//	EditedMenuScreenContent(navController = navController)
//}