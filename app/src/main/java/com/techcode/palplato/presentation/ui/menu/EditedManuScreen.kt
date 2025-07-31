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
	val categoryOptions = listOf("Entradas", "Plato fuerte", "Postres", "Bebidas")
	var expanded by remember { mutableStateOf(false) }
	
	// Imagen del plato
	var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
	val imagePickerLauncher = rememberLauncherForActivityResult(
		contract = ActivityResultContracts.GetContent()
	) { uri: Uri? ->
		selectedImageUri = uri
	}
	
	// Ingredientes principales
	var ingredienteInput by remember { mutableStateOf("") }
	val ingredientesPrincipales = remember { mutableStateListOf<String>() }
	
	// Estado de actualización
	val updateState by viewModel.updateState.collectAsState()
	
	// Autocompletar datos cuando se carga el producto
	LaunchedEffect(selectedProduct) {
		selectedProduct?.let {
			dishName = it.name
			description = it.description
			price = it.price.toString()
			selectedCategory = it.category
			ingredientesPrincipales.clear()
			ingredientesPrincipales.addAll(it.ingredients)
			selectedImageUri = it.imageUrl.takeIf { url -> url.isNotBlank() }?.let { Uri.parse(it) }
		}
	}
	
	// Mostrar el diálogo reutilizable de feedback
	AppAlertDialog(
		state = updateState,
		onDismiss = {
			viewModel.resetUpdateState() // Limpiar estado en el ViewModel
			if (updateState is Resource.Success) {
				navController.popBackStack() // Volver atrás solo si fue exitoso
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
			// Imagen seleccionada
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
			
			OutlinedTextField(
				value = dishName,
				onValueChange = { dishName = it },
				label = { Text("Nombre del plato") },
				modifier = Modifier.fillMaxWidth()
			)
			
			OutlinedTextField(
				value = price,
				onValueChange = { price = it },
				label = { Text("Precio") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth()
			)
			
			OutlinedTextField(
				value = description,
				onValueChange = { description = it },
				label = { Text("Descripción") },
				modifier = Modifier.fillMaxWidth().height(100.dp)
			)
			
			ExposedDropdownMenuBox(
				expanded = expanded,
				onExpandedChange = { expanded = !expanded }
			) {
				OutlinedTextField(
					value = selectedCategory,
					onValueChange = {},
					readOnly = true,
					label = { Text("Categoría") },
					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
					modifier = Modifier.fillMaxWidth().menuAnchor()
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
			
			// Ingredientes
			OutlinedTextField(
				value = ingredienteInput,
				onValueChange = { ingredienteInput = it },
				label = { Text("Ingrediente principal") },
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
			
			Button(
				onClick = {
					selectedProduct?.let {
						val updatedProduct = it.copy(
							name = dishName,
							description = description,
							price = price.toDoubleOrNull() ?: 0.0,
							category = selectedCategory,
							ingredients = ingredientesPrincipales,
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