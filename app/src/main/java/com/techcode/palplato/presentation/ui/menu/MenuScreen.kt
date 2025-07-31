package com.techcode.palplato.presentation.ui.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.viewmodels.auth.ProductViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes
import com.techcode.palplato.presentation.navegation.AppRoutes.EditedMenuRoute
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar
import com.techcode.palplato.utils.Resource

@Composable
fun MenuScreen(	navController: NavController){
	
	MenuScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreenContent(
	navController: NavController,
	viewModel: ProductViewModel = hiltViewModel()
) {
	val tabs = listOf("Todos", "Combos", "Bebidas")
	var selectedTab by rememberSaveable { mutableStateOf(0) }
	
	val productsState by viewModel.productsState.collectAsState()
	
	// Llamar al cargar pantalla
	LaunchedEffect(Unit) {
		viewModel.getProducts()
	}
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Menús", style = MaterialTheme.typography.titleMedium) },
				actions = {
					IconButton(onClick = { }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = { navController.navigate(AppRoutes.CreateMenuScreen) },
				containerColor = Color.Black,
				contentColor = Color.White
			) {
				Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(22.dp))
			}
		},
		bottomBar = { BottomNavigationBar(navController = navController) }
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 16.dp)
		) {
			// Tabs
			ScrollableTabRow(
				selectedTabIndex = selectedTab,
				edgePadding = 0.dp,
				indicator = {},
				divider = {}
			) {
				tabs.forEachIndexed { index, title ->
					Tab(
						selected = selectedTab == index,
						onClick = { selectedTab = index },
						text = {
							Text(
								text = title,
								fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
								color = if (selectedTab == index) Color.Black else Color.Gray
							)
						}
					)
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			when (val state = productsState) {
				is Resource.Loading -> {
					Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
						CircularProgressIndicator()
					}
				}
				
				is Resource.Error -> {
					Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
						Text(
							text = state.message,
							color = Color.Red,
							style = MaterialTheme.typography.bodyMedium
						)
					}
				}
				
				is Resource.Success -> {
					val products = state.result
					if (products.isEmpty()) {
						Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
							Text("No hay menús disponibles", color = Color.Gray)
						}
					} else {
						LazyVerticalGrid(
							columns = GridCells.Fixed(2),
							verticalArrangement = Arrangement.spacedBy(16.dp),
							horizontalArrangement = Arrangement.spacedBy(16.dp),
							modifier = Modifier.fillMaxSize()
						) {
							items(products) { product ->
								MenuCard(
									product = product,
									onClick = {
										navController.navigate(EditedMenuRoute(product.id)) // ✅ Ahora no hay conflicto
									}
								)
								
							}
						}
					}
				}
				
				Resource.Idle -> Unit
			}
		}
	}
}


@Composable
fun MenuCard(
	product: Product,
	onClick: () -> Unit, // ❌ quitar @Composable
	
	onToggleAvailability: () -> Unit = {},
	onDelete: () -> Unit = {}
) {
	Column(
		modifier = Modifier
			.clip(RoundedCornerShape(12.dp))
			.background(Color.White)
			.clickable { onClick() }
			.padding(8.dp)
	) {
		if (product.imageUrl.isNotBlank()) {
			AsyncImage(
				model = product.imageUrl,
				contentDescription = product.name,
				modifier = Modifier
					.height(120.dp)
					.fillMaxWidth()
					.clip(RoundedCornerShape(12.dp)),
				contentScale = ContentScale.Crop
			)
		} else {
			Image(
				painter = painterResource(id = com.techcode.palplato.R.drawable.ic_hamburguesa),
				contentDescription = product.name,
				modifier = Modifier
					.height(120.dp)
					.fillMaxWidth()
					.clip(RoundedCornerShape(12.dp)),
				contentScale = ContentScale.Crop
			)
		}
		
		Spacer(modifier = Modifier.height(8.dp))
		
		Text(
			text = product.name,
			fontWeight = FontWeight.SemiBold,
			style = MaterialTheme.typography.bodyMedium
		)
		
		Text(
			text = "$${product.price}",
			color = Color.Gray,
			style = MaterialTheme.typography.bodySmall,
			maxLines = 2
		)
		
		Spacer(modifier = Modifier.height(12.dp))
		
		Row(
			horizontalArrangement = Arrangement.spacedBy(8.dp),
			modifier = Modifier.fillMaxWidth()
		) {
			// Botón Activar/Desactivar mejorado
			OutlinedButton(
				onClick = { onToggleAvailability() },
				colors = ButtonDefaults.outlinedButtonColors(
					containerColor = if (product.available) Color(0xFF4CAF50).copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.1f),
					contentColor = if (product.available) Color(0xFF2E7D32) else Color(0xFF424242)
				),
				border = BorderStroke(
					1.dp,
					if (product.available) Color(0xFF4CAF50) else Color.Gray
				),
				contentPadding = PaddingValues(12.dp),
				modifier = Modifier.weight(1f),
				shape = RoundedCornerShape(8.dp)
			) {
				Icon(
					imageVector = if (product.available) Icons.Default.CheckCircle else Icons.Default.Cancel,
					contentDescription = if (product.available) "Desactivar producto" else "Activar producto",
					modifier = Modifier.size(20.dp)
				)
			}
			
			// Botón Eliminar mejorado
			OutlinedButton(
				onClick = { onDelete() },
				colors = ButtonDefaults.outlinedButtonColors(
					containerColor = Color(0xFFFFEBEE),
					contentColor = Color(0xFFC62828)
				),
				border = BorderStroke(1.dp, Color(0xFFE57373)),
				contentPadding = PaddingValues(12.dp),
				modifier = Modifier.weight(1f),
				shape = RoundedCornerShape(8.dp)
			) {
				Icon(
					imageVector = Icons.Default.Delete,
					contentDescription = "Eliminar producto",
					modifier = Modifier.size(20.dp)
				)
			}
		}
	}
}



data class MenuItem(
	val title: String,
	val description: String,
	val imageRes: Int
)



//@Preview(showBackground = true)
//@Composable
//fun MenuScreenPreview() {
//
//	val navController = rememberNavController()
//	MenuScreenContent(navController = navController)
//}


//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun OrderDetailsScreenContent(navController: NavController) {
//
//
//
//
//	Scaffold(
//		topBar = {
//			CenterAlignedTopAppBar(
//				title = {
//					Text(
//						text = "Reportes",
//						style = MaterialTheme.typography.titleMedium
//					)
//				},
//				actions = {
//					IconButton(onClick = { /* Acción de notificaciones */ }) {
//						Icon(
//							painter = painterResource(id = R.drawable.ic_notification), // ← Asegúrate de tener este ícono
//							contentDescription = "Notificaciones", modifier = Modifier.size(25.dp)
//						)
//					}
//				}
//			)
//		},
//		bottomBar = {
//			BottomNavigationBar(navController = navController)
//		}
//	) { innerPadding ->
//		Column(
//			modifier = Modifier
//				.padding(innerPadding)
//				.padding(16.dp)
//		) {
//
//
//
//
//
//
//		}
//	}
//
//
//}