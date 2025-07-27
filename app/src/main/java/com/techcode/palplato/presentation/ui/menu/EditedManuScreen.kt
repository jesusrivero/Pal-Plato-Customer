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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.techcode.palplato.R
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun EditedMenuScreen(	navController: NavController){
	
	EditedMenuScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditedMenuScreenContent(navController: NavController) {
	var dishName by rememberSaveable { mutableStateOf("") }
	var description by rememberSaveable { mutableStateOf("") }
	var price by rememberSaveable { mutableStateOf("") }
	var additionalIngredients by rememberSaveable { mutableStateOf("") }
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
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Editar menu", style = MaterialTheme.typography.titleMedium) },
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
			
			Text(
				text = "Agregar imagen del plato",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.align(Alignment.CenterHorizontally)
			)
			
			OutlinedTextField(
				value = dishName,
				onValueChange = { dishName = it },
				label = { Text("Nombre del plato") },
				shape = RoundedCornerShape(16.dp),
				placeholder = { Text("Ej. Arepa Reina Pepiada") },
				modifier = Modifier.fillMaxWidth()
			)
			
			// Ingredientes principales
			OutlinedTextField(
				value = ingredienteInput,
				onValueChange = { ingredienteInput = it },
				label = { Text("Ingrediente principal") },
				trailingIcon = {
					Icon(
						imageVector = Icons.Default.Add,
						contentDescription = "Agregar ingrediente",
						modifier = Modifier
							.clickable {
								if (ingredienteInput.isNotBlank()) {
									ingredientesPrincipales.add(ingredienteInput.trim())
									ingredienteInput = ""
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
				ingredientesPrincipales.forEach { ingrediente ->
					AssistChip(
						onClick = {},
						label = {
							Text(
								text = ingrediente,
								fontSize = 10.sp,
								maxLines = 1,
								modifier = Modifier.widthIn(min = 10.dp)
							)
						},
						trailingIcon = {
							Icon(
								imageVector = Icons.Default.Close,
								contentDescription = "Eliminar ingrediente",
								modifier = Modifier
									.size(12.dp)
									.clickable {
										ingredientesPrincipales.remove(ingrediente)
									}
							)
						},
						modifier = Modifier
							.height(30.dp)
							.defaultMinSize(minWidth = 30.dp),
						shape = RoundedCornerShape(16.dp),
						colors = AssistChipDefaults.assistChipColors(
							containerColor = colorScheme.primaryContainer,
							labelColor = colorScheme.onPrimaryContainer
						)
					)
				}
			}
			
			OutlinedTextField(
				value = price,
				onValueChange = { price = it },
				label = { Text("Precio") },
				shape = RoundedCornerShape(16.dp),
				placeholder = { Text("$.") },
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
				modifier = Modifier.fillMaxWidth()
			)
			
			OutlinedTextField(
				value = additionalIngredients,
				onValueChange = { additionalIngredients = it },
				label = { Text("Descripcion") },
				shape = RoundedCornerShape(16.dp),
				modifier = Modifier
					.fillMaxWidth()
					.height(100.dp)
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
					shape = RoundedCornerShape(16.dp),
					trailingIcon = {
						ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
					},
					modifier = Modifier
						.fillMaxWidth()
						.menuAnchor()
				)
				
				ExposedDropdownMenu(
					expanded = expanded,
					shape = RoundedCornerShape(16.dp),
					modifier = Modifier.background(colorScheme.surfaceVariant),
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
			
			Button(
				onClick = {
				
				},
				modifier = Modifier
					.fillMaxWidth()
					.padding(top = 8.dp),
				colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)),
				shape = RoundedCornerShape(12.dp)
			) {
				Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = Color.Black)
				Spacer(modifier = Modifier.width(8.dp))
				Text("Agregar al menú", color = Color.Black)
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun EditedMenuScreenPreview() {
	
	val navController = rememberNavController()
	EditedMenuScreenContent(navController = navController)
}