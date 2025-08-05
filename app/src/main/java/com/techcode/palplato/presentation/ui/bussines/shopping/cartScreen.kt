package com.techcode.palplato.presentation.ui.bussines.shopping

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun cartScreen(navController: NavController) {
	cartScreenContent(navController = navController)
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun cartScreenContent(navController: NavController) {
	// Datos de ejemplo para los productos del carrito
	var cartItems by remember { mutableStateOf(
		listOf(
			CartItem(
				id = 1,
				name = "Hamburguesa Clásica",
				category = "Hamburguesas",
				price = 15.99,
				quantity = 2,
				imageRes = com.techcode.palplato.R.drawable.ic_hamburguesa  // Reemplaza con tu recurso de imagen
			),
			CartItem(
				id = 2,
				name = "Pizza Margherita",
				category = "Pizzas",
				price = 22.50,
				quantity = 1,
				imageRes = com.techcode.palplato.R.drawable.ic_hamburguesa // Reemplaza con tu recurso de imagen
			),
			CartItem(
				id = 3,
				name = "Papas Fritas",
				category = "Acompañamientos",
				price = 8.99,
				quantity = 3,
				imageRes =  com.techcode.palplato.R.drawable.ic_hamburguesa // Reemplaza con tu recurso de imagen
			),
			CartItem(
				id = 4,
				name = "Coca Cola",
				category = "Bebidas",
				price = 4.50,
				quantity = 2,
				imageRes =  com.techcode.palplato.R.drawable.ic_hamburguesa  // Reemplaza con tu recurso de imagen
			)
		)
	)}
	
	val deliveryFee = 5.00
	val subtotal = cartItems.sumOf { it.price * it.quantity }
	val total = subtotal + deliveryFee
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						"Mi Carrito",
						style = MaterialTheme.typography.titleMedium,
						color = MaterialTheme.colorScheme.onSurface
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = "Atrás",
							modifier = Modifier.size(24.dp)
						)
					}
				},
				actions = {
					IconButton(onClick = { /* Carrito */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_cart),
							contentDescription = "Carrito",
							modifier = Modifier.size(24.dp)
						)
					}
				}
			)
		},
		bottomBar = {
			CartSummaryBottomBar(
				subtotal = subtotal,
				deliveryFee = deliveryFee,
				total = total,
				onContinueClick = {
					// Navegar a la pantalla de pago
					navController.navigate(AppRoutes.ShoppingScreen)
				}
			)
		}
	) { innerPadding ->
		if (cartItems.isEmpty()) {
			EmptyCartContent(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding)
			)
		} else {
			LazyColumn(
				modifier = Modifier
					.fillMaxSize()
					.padding(innerPadding),
				contentPadding = PaddingValues(16.dp),
				verticalArrangement = Arrangement.spacedBy(12.dp)
			) {
				items(cartItems) { item ->
					CartItemCard(
						item = item,
						onQuantityChange = { newQuantity ->
							cartItems = cartItems.map { cartItem ->
								if (cartItem.id == item.id) {
									cartItem.copy(quantity = newQuantity)
								} else {
									cartItem
								}
							}.filter { it.quantity > 0 }
						}
					)
				}
				
				item {
					Spacer(modifier = Modifier.height(100.dp)) // Espacio para el bottom bar
				}
			}
		}
	}
}

@Composable
fun CartItemCard(
	item: CartItem,
	onQuantityChange: (Int) -> Unit
) {
	Card(
		modifier = Modifier.fillMaxWidth(),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			// Imagen del producto
			Card(
				modifier = Modifier.size(80.dp),
				shape = RoundedCornerShape(8.dp),
				elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
			) {
				Image(
					painter = painterResource(id = item.imageRes),
					contentDescription = item.name,
					modifier = Modifier.fillMaxSize(),
					contentScale = ContentScale.Crop
				)
			}
			
			Spacer(modifier = Modifier.width(16.dp))
			
			// Información del producto
			Column(
				modifier = Modifier.weight(1f)
			) {
				Text(
					text = item.name,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onSurface
				)
				Text(
					text = item.category,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Spacer(modifier = Modifier.height(8.dp))
				Text(
					text = "$${String.format("%.2f", item.price)}",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.primary
				)
			}
			
			// Controles de cantidad
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(8.dp)
			) {
				IconButton(
					onClick = {
						if (item.quantity > 0) {
							onQuantityChange(item.quantity - 1)
						}
					},
					modifier = Modifier.size(28.dp)
				) {
					Icon(
						painter = painterResource(id = com.techcode.palplato.R.drawable.ic_subtract),
						contentDescription = "Restar",
						tint = MaterialTheme.colorScheme.primary
					)
				}
				
				Text(
					text = item.quantity.toString(),
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.widthIn(min = 24.dp),
					textAlign = TextAlign.Center
				)
				
				IconButton(
					onClick = { onQuantityChange(item.quantity + 1) },
					modifier = Modifier.size(32.dp)
				) {
					Icon(
						painter = painterResource(id = com.techcode.palplato.R.drawable.ic_add),
						contentDescription = "Sumar",
						tint = MaterialTheme.colorScheme.primary
					)
				}
			}
		}
	}
}

@Composable
fun CartSummaryBottomBar(
	subtotal: Double,
	deliveryFee: Double,
	total: Double,
	onContinueClick: () -> Unit
) {
	Surface(
		modifier = Modifier.fillMaxWidth(),
		color = MaterialTheme.colorScheme.surface,
		shadowElevation = 8.dp
	) {
		Column(
			modifier = Modifier
				.padding(16.dp)
				.padding(bottom = WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding())
		) {
			// Resumen de costos
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = "Subtotal:",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Text(
					text = "$${String.format("%.2f", subtotal)}",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
			}
			
			Spacer(modifier = Modifier.height(4.dp))
			
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = "Delivery:",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Text(
					text = "$${String.format("%.2f", deliveryFee)}",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
			}
			
			Divider(
				modifier = Modifier.padding(vertical = 8.dp),
				color = MaterialTheme.colorScheme.outline
			)
			
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Text(
					text = "Total:",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onSurface
				)
				Text(
					text = "$${String.format("%.2f", total)}",
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.primary
				)
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			// Botón de continuar
			Button(
				onClick = onContinueClick,
				modifier = Modifier.fillMaxWidth(),
				shape = RoundedCornerShape(8.dp),
				colors = ButtonDefaults.buttonColors(
					containerColor = MaterialTheme.colorScheme.primary
				)
			) {
				Text(
					text = "Continuar con la Compra",
					style = MaterialTheme.typography.titleMedium,
					modifier = Modifier.padding(vertical = 4.dp)
				)
			}
		}
	}
}

@Composable
fun EmptyCartContent(
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier,
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center
	) {
		Icon(
			painter = painterResource(id = com.techcode.palplato.R.drawable.ic_cart),
			contentDescription = "Carrito vacío",
			modifier = Modifier.size(120.dp),
			tint = MaterialTheme.colorScheme.onSurfaceVariant
		)
		
		Spacer(modifier = Modifier.height(16.dp))
		
		Text(
			text = "Tu carrito está vacío",
			style = MaterialTheme.typography.titleLarge,
			color = MaterialTheme.colorScheme.onSurface
		)
		
		Text(
			text = "Agrega algunos productos para comenzar",
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurfaceVariant,
			textAlign = TextAlign.Center
		)
	}
}

// Data class para los items del carrito
data class CartItem(
	val id: Int,
	val name: String,
	val category: String,
	val price: Double,
	val quantity: Int,
	val imageRes: Int
)