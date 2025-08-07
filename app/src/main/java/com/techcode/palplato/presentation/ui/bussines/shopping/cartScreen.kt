package com.techcode.palplato.presentation.ui.bussines.shopping

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.CartItem
import com.techcode.palplato.domain.viewmodels.auth.CartViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun cartScreen(navController: NavController) {
	CartScreenContent(navController = navController)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreenContent(
	navController: NavController,
	viewModel: CartViewModel = hiltViewModel()
) {
	val cartItems by viewModel.cartItems.collectAsState()
	val subtotal by viewModel.subtotal.collectAsState()
	val deliveryFee by viewModel.deliveryCost.collectAsState()
	val total by viewModel.total.collectAsState()
	
	val cartItemCount = cartItems.sumOf { it.quantity }
	
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
					Box {
						IconButton(onClick = { /* Acción carrito */ }) {
							Icon(
								painter = painterResource(id = com.techcode.palplato.R.drawable.ic_cart),
								contentDescription = "Carrito",
								modifier = Modifier.size(24.dp)
							)
						}
						
						if (cartItemCount > 0) {
							Box(
								modifier = Modifier
									.align(Alignment.TopEnd)
									.offset(x = 8.dp, y = (-2).dp) // Ajustar posición
									.size(18.dp)
									.background(
										color = MaterialTheme.colorScheme.primary,
										shape = CircleShape
									),
								contentAlignment = Alignment.Center
							) {
								Text(
									text = cartItemCount.toString(),
									color = Color.White,
									style = MaterialTheme.typography.labelSmall,
									fontWeight = FontWeight.Bold
								)
							}
						}
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
							if (newQuantity > 0) {
								viewModel.updateQuantity(item.productId, newQuantity)
							} else {
								viewModel.removeItem(item.productId)
							}
						}
					)
				}
				
				item {
					Spacer(modifier = Modifier.height(100.dp))
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
				AsyncImage(
					model = item.imageUrl,
					contentDescription = item.productName,
					contentScale = ContentScale.Crop,
					modifier = Modifier.fillMaxSize()
				)
			}
			
			Spacer(modifier = Modifier.width(16.dp))
			
			// Información del producto
			Column(
				modifier = Modifier.weight(1f)
			) {
				Text(
					text = item.productName,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.onSurface
				)
				Spacer(modifier = Modifier.height(4.dp))
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
					onClick = { onQuantityChange(item.quantity - 1) },
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

