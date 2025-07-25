package com.techcode.palplato.presentation.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Category
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun CreateBussinessScreen(	navController: NavController){
	
	CreateBussinessContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateBussinessContent(navController: NavController) {
	var businessName by rememberSaveable { mutableStateOf("") }
	var businessPhone by rememberSaveable { mutableStateOf("") }
	var businessAddress by rememberSaveable { mutableStateOf("") }
	
	val availableCategories = listOf("Hamburguesas", "Pizza", "Sushi", "Comida Mexicana", "Bebidas", "Helados")
	
	val suggestedProducts = mapOf(
		"Hamburguesas" to listOf("Clásicas", "Dobles", "Con tocineta"),
		"Pizza" to listOf("A la leña", "Pepperoni", "Hawaiana"),
		"Sushi" to listOf("California roll", "Nigiri", "Tempura roll"),
		"Comida Mexicana" to listOf("Tacos", "Burritos", "Quesadillas"),
		"Bebidas" to listOf("Jugos", "Gaseosas", "Smoothies"),
		"Helados" to listOf("Chocolate", "Vainilla", "Fresa")
	)
	
	var expanded by remember { mutableStateOf(false) }
	val selectedCategories = remember { mutableStateListOf<String>() }
	var selectedCategory by remember { mutableStateOf("") }
	var productInput by remember { mutableStateOf("") }
	val productList = remember { mutableStateListOf<String>() }
	
	// Autocompletar productos al seleccionar categoría
	fun addCategory(category: String) {
		if (category !in selectedCategories && selectedCategories.size < 1) {
			selectedCategories.add(category)
			suggestedProducts[category]?.forEach {
				if (it !in productList) productList.add(it)
			}
		}
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Crear negocio", style = MaterialTheme.typography.titleMedium) },
				actions = {
					IconButton(onClick = { /* Acción de soporte o notificaciones */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_supports),
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
			
			Text("Datos del negocio", style = MaterialTheme.typography.titleLarge)
			
			OutlinedTextField(
				value = businessName,
				onValueChange = { businessName = it },
				label = { Text("Nombre del negocio") },
				leadingIcon = { Icon(Icons.Default.Business, contentDescription = null) },
				singleLine = true,
				shape = RoundedCornerShape(16.dp),
				modifier = Modifier.fillMaxWidth()
			)
			
			// Categorías (máximo 2)
			Text("Categoría", style = MaterialTheme.typography.bodyLarge)
			
			ExposedDropdownMenuBox(
				expanded = expanded,
				onExpandedChange = { expanded = !expanded }
			) {
				OutlinedTextField(
					readOnly = true,
					value = if (selectedCategory.isNotBlank()) selectedCategory else "Selecciona",
					onValueChange = {},
					label = { Text("Categoría") },
					trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
					modifier = Modifier.menuAnchor().fillMaxWidth(),
					shape = RoundedCornerShape(16.dp)
				)
				
				ExposedDropdownMenu(
					expanded = expanded,
					onDismissRequest = { expanded = false }
				) {
					availableCategories.forEach { category ->
						DropdownMenuItem(
							text = { Text(category) },
							onClick = {
								selectedCategory = category
								expanded = false
								// Limpiamos productos anteriores y agregamos sugeridos
								productList.clear()
								suggestedProducts[category]?.forEach {
									productList.add(it)
								}
							}
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
								fontSize = 8.sp,
								maxLines = 1
							)
						},
						trailingIcon = {
							Icon(
								imageVector = Icons.Default.Close,
								contentDescription = "Eliminar producto",
								modifier = Modifier
									.size(10.dp)
									.clickable {
										productList.remove(product)
									}
							)
						},
						modifier = Modifier
							.padding(2.dp)
							.height(20.dp),
						shape = RoundedCornerShape(16.dp),
						colors = AssistChipDefaults.assistChipColors(
							containerColor = MaterialTheme.colorScheme.primaryContainer,
							labelColor = MaterialTheme.colorScheme.onPrimaryContainer
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
				keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
				shape = RoundedCornerShape(16.dp),
				modifier = Modifier.fillMaxWidth()
			)
			
			// Dirección
			OutlinedTextField(
				value = businessAddress,
				onValueChange = { businessAddress = it },
				label = { Text("Dirección (opcional)") },
				leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
				singleLine = false,
				minLines = 2,
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
				onClick = {
					navController.navigate(AppRoutes.MainScreen)
				},
				shape = RoundedCornerShape(12.dp),
				modifier = Modifier.fillMaxWidth()
			) {
				Text("Guardar y continuar")
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
fun SCreateBussinessPreview() {
	
	val navController = rememberNavController()
	CreateBussinessContent(navController = navController)
}