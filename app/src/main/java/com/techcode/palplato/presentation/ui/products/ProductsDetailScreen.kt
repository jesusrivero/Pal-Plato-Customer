package com.techcode.palplato.presentation.ui.products


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.LocalDrink
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.viewmodels.auth.ProductViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun ProductDetailScreen(
	navController: NavController,
	businessId: String,
	productId: String
) {
	ProductsDetailScreenContent(
		navController = navController,
		businessId = businessId,
		productId = productId
	)
}


@Composable
fun ProductsDetailScreenContent(
	navController: NavController,
	businessId: String,
	productId: String,
	viewModel: ProductViewModel = hiltViewModel()
) {
	val product by viewModel.getProductById(businessId, productId).collectAsState(initial = null)
	
	if (product == null) {
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			CircularProgressIndicator()
		}
	} else {
		ProductDetailUI(navController, product!!)
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailUI(navController: NavController, product: Product) {
	var cantidad by remember { mutableStateOf(1) }
	var notas by remember { mutableStateOf("") }
	val precioTotal = product.price * cantidad
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = { },
				navigationIcon = {
					IconButton(onClick = { navController.navigateUp() }) {
						Icon(
							Icons.Default.ArrowBack,
							contentDescription = "Volver"
						)
					}
				},
				actions = {
					IconButton(onClick = { navController.navigate(AppRoutes.cartScreen) }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_cart),
							contentDescription = "Carrito",
							modifier = Modifier.size(24.dp)
						)
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = Color.Transparent
				)
			)
		},
		bottomBar = {
			Surface(
				modifier = Modifier
					.fillMaxWidth()
					.navigationBarsPadding(),
				color = MaterialTheme.colorScheme.surface
			) {
				Column(
					modifier = Modifier.padding(16.dp)
				) {
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.CenterVertically
					) {
						Text(
							text = "Total:",
							style = MaterialTheme.typography.titleMedium,
							color = MaterialTheme.colorScheme.onSurface
						)
						Text(
							text = "$${"%.2f".format(precioTotal)}",
							style = MaterialTheme.typography.headlineSmall,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.primary
						)
					}
					Spacer(modifier = Modifier.height(12.dp))
					
					// Dos botones en la misma fila
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						// Botón agregar al carrito
						Button(
							onClick = {
								// TODO: Agregar al carrito con cantidad y notas
							},
							modifier = Modifier
								.weight(1f),
							shape = RoundedCornerShape(16.dp),
							enabled = product.available
						) {
							Row(
								verticalAlignment = Alignment.CenterVertically,
								horizontalArrangement = Arrangement.Center
							) {
								Icon(
									Icons.Default.ShoppingCart,
									contentDescription = null,
									modifier = Modifier.size(20.dp)
								)
								Spacer(modifier = Modifier.width(6.dp))
								Text(
									text = "Agregar",
									style = MaterialTheme.typography.titleMedium
								)
							}
						}
						
						// Botón comprar ahora
						Button(
							onClick = {
								// TODO: Comprar directamente
							},
							modifier = Modifier
								.weight(1f),
							shape = RoundedCornerShape(16.dp),
							enabled = product.available,
							colors = ButtonDefaults.buttonColors(
								containerColor = MaterialTheme.colorScheme.primaryContainer,
								contentColor = MaterialTheme.colorScheme.onPrimaryContainer
							)
						) {
							Row(
								verticalAlignment = Alignment.CenterVertically,
								horizontalArrangement = Arrangement.Center
							) {
								Icon(
									Icons.Default.Payment,
									contentDescription = null,
									modifier = Modifier.size(20.dp)
								)
								Spacer(modifier = Modifier.width(6.dp))
								Text(
									text = "Comprar",
									style = MaterialTheme.typography.titleMedium
								)
							}
						}
					}
				}
			}
		}
	
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding)
		) {
			// IMAGEN DEL PRODUCTO
			item {
				Box {
					AsyncImage(
						model = product.imageUrl,
						contentDescription = product.name,
						contentScale = ContentScale.Crop,
						modifier = Modifier
							.fillMaxWidth()
							.height(280.dp)
							.clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
					)
					
					// Badge de disponibilidad
					if (!product.available) {
						Box(
							modifier = Modifier
								.align(Alignment.TopEnd)
								.padding(16.dp)
								.background(
									MaterialTheme.colorScheme.error,
									RoundedCornerShape(20.dp)
								)
								.padding(horizontal = 12.dp, vertical = 6.dp)
						) {
							Text(
								text = "No disponible",
								color = MaterialTheme.colorScheme.onError,
								style = MaterialTheme.typography.labelSmall,
								fontWeight = FontWeight.Medium
							)
						}
					}
				}
			}
			
			// INFORMACIÓN PRINCIPAL
			item {
				Column(
					modifier = Modifier
						.fillMaxWidth()
						.padding(20.dp)
				) {
					// Nombre y precio
					Row(
						modifier = Modifier.fillMaxWidth(),
						horizontalArrangement = Arrangement.SpaceBetween,
						verticalAlignment = Alignment.Top
					) {
						Column(modifier = Modifier.weight(1f)) {
							Text(
								text = product.name,
								style = MaterialTheme.typography.headlineMedium,
								fontWeight = FontWeight.Bold,
								modifier = Modifier.padding(end = 16.dp)
							)
							
							// Categoría
							if (product.category.isNotEmpty()) {
								Text(
									text = product.category,
									style = MaterialTheme.typography.labelLarge,
									color = MaterialTheme.colorScheme.primary,
									modifier = Modifier
										.background(
											MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
											RoundedCornerShape(8.dp)
										)
										.padding(horizontal = 8.dp, vertical = 4.dp)
								)
							}
						}
						
						Text(
							text = "$${"%.2f".format(product.price)}",
							style = MaterialTheme.typography.headlineSmall,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.primary
						)
					}
					
					Spacer(modifier = Modifier.height(16.dp))
					
					// Tiempo de preparación y tamaño/tipo (si aplica)
					Row(
						horizontalArrangement = Arrangement.spacedBy(16.dp)
					) {
						if (product.preparationTime > 0) {
							InfoChip(
								icon = Icons.Default.Schedule,
								text = "${product.preparationTime} min"
							)
						}
						
						product.size?.let { size ->
							InfoChip(
								icon = Icons.Default.LocalDrink,
								text = size
							)
						}
						
						product.type?.let { type ->
							InfoChip(
								icon = Icons.Default.Category,
								text = type
							)
						}
					}
					
					Spacer(modifier = Modifier.height(20.dp))
					
					// Descripción
					if (product.description.isNotEmpty()) {
						Text(
							text = "Descripción",
							style = MaterialTheme.typography.titleMedium,
							fontWeight = FontWeight.SemiBold
						)
						Spacer(modifier = Modifier.height(8.dp))
						Text(
							text = product.description,
							style = MaterialTheme.typography.bodyLarge,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							lineHeight = 24.sp
						)
						Spacer(modifier = Modifier.height(20.dp))
					}
					
					// Ingredientes
					if (product.ingredients.isNotEmpty()) {
						Text(
							text = "Ingredientes",
							style = MaterialTheme.typography.titleMedium,
							fontWeight = FontWeight.SemiBold
						)
						Spacer(modifier = Modifier.height(12.dp))
						LazyRow(
							horizontalArrangement = Arrangement.spacedBy(8.dp)
						) {
							items(product.ingredients) { ingredient ->
								Surface(
									shape = RoundedCornerShape(20.dp),
									color = MaterialTheme.colorScheme.secondaryContainer
								) {
									Text(
										text = ingredient,
										modifier = Modifier.padding(
											horizontal = 12.dp,
											vertical = 8.dp
										),
										style = MaterialTheme.typography.bodyMedium,
										color = MaterialTheme.colorScheme.onSecondaryContainer
									)
								}
							}
						}
						Spacer(modifier = Modifier.height(24.dp))
					}
				}
			}
			
			// SELECTOR DE CANTIDAD
			item {
				Card(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 20.dp),
					shape = RoundedCornerShape(16.dp),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
					)
				) {
					Column(
						modifier = Modifier.padding(20.dp)
					) {
						Text(
							text = "Cantidad",
							style = MaterialTheme.typography.titleMedium,
							fontWeight = FontWeight.SemiBold
						)
						Spacer(modifier = Modifier.height(12.dp))
						
						Row(
							verticalAlignment = Alignment.CenterVertically,
							horizontalArrangement = Arrangement.SpaceBetween,
							modifier = Modifier.fillMaxWidth()
						) {
							Row(
								verticalAlignment = Alignment.CenterVertically
							) {
								FilledIconButton(
									onClick = { if (cantidad > 1) cantidad-- },
									modifier = Modifier.size(48.dp)
								) {
									Icon(Icons.Default.Remove, contentDescription = "Disminuir")
								}
								
								Text(
									text = cantidad.toString(),
									style = MaterialTheme.typography.headlineSmall,
									fontWeight = FontWeight.Bold,
									modifier = Modifier
										.width(60.dp)
										.padding(horizontal = 16.dp),
									textAlign = TextAlign.Center
								)
								
								FilledIconButton(
									onClick = { cantidad++ },
									modifier = Modifier.size(48.dp)
								) {
									Icon(Icons.Default.Add, contentDescription = "Aumentar")
								}
							}
						}
					}
				}
				Spacer(modifier = Modifier.height(20.dp))
			}
			
			// NOTAS DEL PEDIDO
			item {
				Card(
					modifier = Modifier
						.fillMaxWidth()
						.padding(horizontal = 20.dp),
					shape = RoundedCornerShape(16.dp),
					colors = CardDefaults.cardColors(
						containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
					)
				) {
					Column(
						modifier = Modifier.padding(20.dp)
					) {
						Row(
							verticalAlignment = Alignment.CenterVertically
						) {
							Icon(
								Icons.Default.Edit,
								contentDescription = null,
								tint = MaterialTheme.colorScheme.primary,
								modifier = Modifier.size(20.dp)
							)
							Spacer(modifier = Modifier.width(8.dp))
							Text(
								text = "Notas especiales",
								style = MaterialTheme.typography.titleMedium,
								fontWeight = FontWeight.SemiBold
							)
						}
						Spacer(modifier = Modifier.height(8.dp))
						Text(
							text = "Cuéntanos cómo prefieres tu pedido",
							style = MaterialTheme.typography.bodyMedium,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
						Spacer(modifier = Modifier.height(16.dp))
						
						OutlinedTextField(
							value = notas,
							onValueChange = { notas = it },
							placeholder = {
								Text(
									"Ej: Sin cebolla, extra queso, término medio...",
									style = MaterialTheme.typography.bodyMedium
								)
							},
							modifier = Modifier
								.fillMaxWidth()
								.height(120.dp),
							shape = RoundedCornerShape(12.dp),
							colors = OutlinedTextFieldDefaults.colors(
								focusedBorderColor = MaterialTheme.colorScheme.primary,
								unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
							)
						)
					}
				}
				Spacer(modifier = Modifier.height(100.dp))
			}
		}
	}
}

@Composable
private fun InfoChip(
	icon: ImageVector,
	text: String
) {
	Surface(
		shape = RoundedCornerShape(20.dp),
		color = MaterialTheme.colorScheme.primaryContainer
	) {
		Row(
			modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
			verticalAlignment = Alignment.CenterVertically
		) {
			Icon(
				imageVector = icon,
				contentDescription = null,
				modifier = Modifier.size(16.dp),
				tint = MaterialTheme.colorScheme.onPrimaryContainer
			)
			Spacer(modifier = Modifier.width(6.dp))
			Text(
				text = text,
				style = MaterialTheme.typography.labelMedium,
				color = MaterialTheme.colorScheme.onPrimaryContainer,
				fontWeight = FontWeight.Medium
			)
		}
	}
}
