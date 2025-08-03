package com.techcode.palplato.presentation.ui.favorites


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController


@Composable
fun FavoriteScreen(	navController: NavController){
	
	FavoritesScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritesScreenContent(
	navController: NavController,
) {
	val favoriteBusinesses = listOf(
		FavoriteBusiness("Burger House", "Hamburguesas", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "15-25 min", true, "$3.500", "2x1 Combo", 156, "Abierto hasta 12:00 AM", "Tu favorito desde 2023"),
		FavoriteBusiness("SushiGo", "Sushi",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.9f, "25-35 min", true, "GRATIS", "Premium", 89, "Abierto hasta 11:00 PM", "El mejor sushi de la ciudad"),
		FavoriteBusiness("La Parrilla", "Parrilla",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.7f, "20-30 min", true, "$4.000", "Especial fin de semana", 203, "Abierto hasta 1:00 AM", "Carne premium a la parrilla"),
		FavoriteBusiness("Veggie Life", "Vegetariano",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.5f, "18-28 min", true, "$2.500", "Saludable", 78, "Abierto hasta 10:00 PM", "Opciones 100% veganas"),
		FavoriteBusiness("Café Central", "Café & Postres",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "5-15 min", true, "GRATIS", "Nuevo menú", 134, "Abierto 24 horas", "El mejor café de especialidad"),
		FavoriteBusiness("Pizzería Donato", "Pizza",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.6f, "20-30 min", true, "$3.000", "Pizza familiar", 167, "Abierto hasta 12:30 AM", "Recetas tradicionales italianas")
	)
	
	val favoriteDishes = listOf(
		FavoriteDish("Big Mac Deluxe", "Burger House", "$18.900",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "Tu favorito de siempre", 24),
		FavoriteDish("Salmon Nigiri (8 pzs)", "SushiGo", "$24.500",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.9f, "Salmón fresco premium", 18),
		FavoriteDish("Parrillada Completa", "La Parrilla", "$45.900",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.7f, "Para 2 personas", 32),
		FavoriteDish("Bowl Quinoa Power", "Veggie Life", "$16.500",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.6f, "Súper nutritivo", 15),
		FavoriteDish("Latte Especial", "Café Central", "$8.900", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "Con arte latte", 41),
		FavoriteDish("Pizza Margherita", "Pizzería Donato", "$28.900",  com.techcode.palplato.R.drawable.ic_hamburguesa, 4.5f, "Clásica italiana", 27)
	)
	
	val quickOrders = listOf(
		QuickOrder("Pedido habitual", "Big Mac + Papas + Coca", "$25.900", "Burger House", "Último: Hace 3 días"),
		QuickOrder("Combinación sushi", "10 Rolls variados", "$42.500", "SushiGo", "Último: Hace 1 semana"),
		QuickOrder("Desayuno especial", "Tostadas + Café + Jugo", "$18.500", "Café Central", "Último: Ayer")
	)
	
	val categories = listOf("Todos", "Restaurantes", "Platos", "Bebidas", "Postres")
	var selectedCategory by remember { mutableStateOf("Todos") }
	var showGrid by remember { mutableStateOf(true) }
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Column {
						Text(
							"Mis Favoritos",
							style = MaterialTheme.typography.titleLarge,
							fontWeight = FontWeight.Bold
						)
					}
				},
				actions = {
					IconButton(onClick = { /* Notificaciones */ }) {
						Box {
							Icon(
								painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
								contentDescription = "Notificaciones",
								modifier = Modifier.size(24.dp)
							)
							Surface(
								modifier = Modifier
									.size(8.dp)
									.align(Alignment.TopEnd)
									.offset(x = 2.dp, y = 2.dp),
								shape = CircleShape,
								color = Color.Red
							) {}
						}
					}
				},
				colors = TopAppBarDefaults.topAppBarColors(
					containerColor = MaterialTheme.colorScheme.surface
				)
			)
		},
		bottomBar = { BottomNavigationBar(navController = navController) }
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(20.dp)
		) {
			// Stats card
			item {
				FavoritesStatsCard(favoriteBusinesses.size, favoriteDishes.size)
			}
			
			
			// Filtros de categoría
			item {
				LazyRow(
					modifier = Modifier.fillMaxWidth(),
					contentPadding = PaddingValues(horizontal = 16.dp),
					horizontalArrangement = Arrangement.spacedBy(8.dp)
				) {
					items(categories) { category ->
						FilterChip(
							onClick = { selectedCategory = category },
							label = { Text(category, fontSize = 12.sp) },
							selected = selectedCategory == category,
							colors = FilterChipDefaults.filterChipColors(
								selectedContainerColor = MaterialTheme.colorScheme.primary,
								selectedLabelColor = MaterialTheme.colorScheme.onPrimary
							)
						)
					}
				}
			}
			
			
			// Platos favoritos
			item {
				Column {
					SectionHeader(
						title = "Platos Favoritos",
						subtitle = "Los que más pides",
						icon = Icons.Default.Restaurant,
						onSeeAllClick = { /* Ver todos */ }
					)
					LazyRow(
						modifier = Modifier.fillMaxWidth(),
						contentPadding = PaddingValues(horizontal = 16.dp),
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						items(favoriteDishes) { dish ->
							FavoriteDishCard(dish) {
								// Agregar al carrito
							}
						}
					}
				}
			}
			
			// Restaurantes favoritos con vista seleccionable
			item {
				Column {
					SectionHeader(
						title = "Restaurantes Favoritos",
						subtitle = "Tus lugares de confianza",
						icon = Icons.Default.Favorite,
						onSeeAllClick = { /* Ver todos */ }
					)
				}
			}
			
			// Grid o lista de restaurantes
			if (showGrid) {
				items(favoriteBusinesses.chunked(2)) { businessPair ->
					Row(
						modifier = Modifier
							.fillMaxWidth()
							.padding(horizontal = 16.dp),
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						businessPair.forEach { business ->
							FavoriteBusinessGridCard(
								business = business,
								modifier = Modifier.weight(1f)
							) {
								// Navegación al restaurante
							}
						}
						// Rellenar espacio si hay número impar
						if (businessPair.size == 1) {
							Spacer(modifier = Modifier.weight(1f))
						}
					}
				}
			} else {
				items(favoriteBusinesses) { business ->
					FavoriteBusinessListCard(
						business = business,
						modifier = Modifier.padding(horizontal = 16.dp)
					) {
						// Navegación al restaurante
					}
				}
			}
			
			
			// Espacio final
			item {
				Spacer(modifier = Modifier.height(16.dp))
			}
		}
	}
}

@Composable
fun FavoritesStatsCard(businessCount: Int, dishCount: Int) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
		)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(20.dp),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			StatItem(
				icon = Icons.Default.Store,
				count = businessCount,
				label = "Restaurantes",
				color = Color(0xFF4CAF50)
			)
			
			Divider(
				modifier = Modifier
					.height(40.dp)
					.width(1.dp),
				color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
			)
			
			StatItem(
				icon = Icons.Default.Restaurant,
				count = dishCount,
				label = "Platos",
				color = Color(0xFFFF9800)
			)
			
			Divider(
				modifier = Modifier
					.height(40.dp)
					.width(1.dp),
				color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
			)
			
			StatItem(
				icon = Icons.Default.TrendingUp,
				count = 42,
				label = "Pedidos",
				color = Color(0xFFE91E63)
			)
		}
	}
}

@Composable
fun StatItem(
	icon: ImageVector,
	count: Int,
	label: String,
	color: Color
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Surface(
			shape = CircleShape,
			color = color.copy(alpha = 0.1f)
		) {
			Icon(
				icon,
				contentDescription = label,
				tint = color,
				modifier = Modifier.padding(8.dp)
			)
		}
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = count.toString(),
			style = MaterialTheme.typography.titleMedium,
			fontWeight = FontWeight.Bold
		)
		Text(
			text = label,
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
}

@Composable
fun QuickOrderCard(
	order: QuickOrder,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(220.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = Color(0xFF4CAF50).copy(alpha = 0.1f)
		),
		border = BorderStroke(1.dp, Color(0xFF4CAF50).copy(alpha = 0.3f))
	) {
		Column(
			modifier = Modifier.padding(12.dp)
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.Top
			) {
				Column(modifier = Modifier.weight(1f)) {
					Text(
						text = order.name,
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Bold
					)
					Text(
						text = order.description,
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)
				}
				Surface(
					shape = CircleShape,
					color = Color(0xFF4CAF50)
				) {
					Icon(
						Icons.Default.Replay,
						contentDescription = "Repetir",
						tint = Color.White,
						modifier = Modifier.padding(6.dp).size(16.dp)
					)
				}
			}
			
			Spacer(modifier = Modifier.height(8.dp))
			
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.Bottom
			) {
				Column {
					Text(
						text = order.price,
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.primary
					)
					Text(
						text = order.restaurant,
						style = MaterialTheme.typography.bodySmall,
						fontSize = 10.sp,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				Text(
					text = order.lastOrder,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 9.sp,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
	}
}



@Composable
fun FavoriteDishCard(
	dish: FavoriteDish,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(160.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(4.dp)
	) {
		Column {
			Box {
				Image(
					painter = painterResource(id = dish.imageRes),
					contentDescription = dish.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.fillMaxWidth()
						.height(80.dp)
				)
				
				Surface(
					modifier = Modifier
						.align(Alignment.TopEnd)
						.padding(6.dp),
					shape = CircleShape,
					color = Color.Red
				) {
					Icon(
						Icons.Default.Favorite,
						contentDescription = "Favorito",
						tint = Color.White,
						modifier = Modifier.padding(4.dp).size(12.dp)
					)
				}
				
				Surface(
					modifier = Modifier
						.align(Alignment.BottomStart)
						.padding(6.dp),
					shape = RoundedCornerShape(8.dp),
					color = Color.Black.copy(alpha = 0.7f)
				) {
					Text(
						text = "${dish.orderCount} pedidos",
						color = Color.White,
						fontSize = 9.sp,
						fontWeight = FontWeight.Bold,
						modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
					)
				}
			}
			
			Column(modifier = Modifier.padding(8.dp)) {
				Text(
					text = dish.name,
					style = MaterialTheme.typography.bodySmall,
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = dish.restaurant,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 10.sp,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				Text(
					text = dish.description,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 9.sp,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = dish.price,
						style = MaterialTheme.typography.bodySmall,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.primary
					)
					
					Surface(
						shape = CircleShape,
						color = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(20.dp)
					) {
						IconButton(
							onClick = onClick,
							modifier = Modifier.size(20.dp)
						) {
							Icon(
								Icons.Default.Add,
								contentDescription = "Agregar",
								tint = Color.White,
								modifier = Modifier.size(10.dp)
							)
						}
					}
				}
			}
		}
	}
}




@Composable
fun FavoriteBusinessGridCard(
	business: FavoriteBusiness,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier.clickable { onClick() },
		shape = RoundedCornerShape(16.dp),
		elevation = CardDefaults.cardElevation(4.dp)
	) {
		Column {
			Box {
				Image(
					painter = painterResource(id = business.imageRes),
					contentDescription = business.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.fillMaxWidth()
						.height(120.dp)
				)
				
				// Badges superiores
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					if (business.badge.isNotEmpty()) {
						Surface(
							shape = RoundedCornerShape(8.dp),
							color = when {
								business.badge.contains("2x1") -> Color(0xFFE91E63)
								business.badge.contains("Premium") -> Color(0xFFFF9800)
								business.badge.contains("Especial") -> Color(0xFF9C27B0)
								business.badge.contains("Saludable") -> Color(0xFF4CAF50)
								business.badge.contains("Nuevo") -> Color(0xFF2196F3)
								else -> Color.Black
							}.copy(alpha = 0.9f)
						) {
							Text(
								text = business.badge,
								color = Color.White,
								fontSize = 9.sp,
								fontWeight = FontWeight.Bold,
								modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
							)
						}
					} else {
						Spacer(modifier = Modifier)
					}
					
					Surface(
						shape = CircleShape,
						color = Color.Red
					) {
						Icon(
							Icons.Default.Favorite,
							contentDescription = "Favorito",
							tint = Color.White,
							modifier = Modifier.padding(4.dp).size(12.dp)
						)
					}
				}
				
				// Información de entrega
				Surface(
					modifier = Modifier
						.align(Alignment.BottomStart)
						.padding(8.dp),
					shape = RoundedCornerShape(6.dp),
					color = Color.Black.copy(alpha = 0.8f)
				) {
					Text(
						text = if (business.deliveryPrice == "GRATIS") "🚚 GRATIS" else "🚚 ${business.deliveryPrice}",
						color = if (business.deliveryPrice == "GRATIS") Color(0xFF4CAF50) else Color.White,
						fontSize = 9.sp,
						fontWeight = FontWeight.Bold,
						modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
					)
				}
			}
			
			Column(modifier = Modifier.padding(12.dp)) {
				Text(
					text = business.name,
					style = MaterialTheme.typography.bodyMedium,
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				
				Text(
					text = business.category,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					fontSize = 11.sp
				)
				
				Text(
					text = business.specialNote,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 10.sp,
					color = MaterialTheme.colorScheme.primary,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis,
					modifier = Modifier.padding(vertical = 2.dp)
				)
				
				Spacer(modifier = Modifier.height(4.dp))
				
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							Icons.Default.Star,
							contentDescription = "Rating",
							tint = Color(0xFFFFB800),
							modifier = Modifier.size(12.dp)
						)
						Text(
							text = business.rating.toString(),
							style = MaterialTheme.typography.bodySmall,
							fontSize = 10.sp,
							fontWeight = FontWeight.Medium,
							modifier = Modifier.padding(start = 2.dp)
						)
						Text(
							text = " (${business.reviewCount})",
							style = MaterialTheme.typography.bodySmall,
							fontSize = 9.sp,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
					
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							Icons.Default.AccessTime,
							contentDescription = "Tiempo",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(10.dp)
						)
						Text(
							text = business.deliveryTime.split("-")[0] + "min",
							style = MaterialTheme.typography.bodySmall,
							fontSize = 9.sp,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.padding(start = 2.dp)
						)
					}
				}
				
				Text(
					text = business.schedule,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 9.sp,
					color = if (business.schedule.contains("Abierto")) Color(0xFF4CAF50) else Color(0xFFE91E63),
					modifier = Modifier.padding(top = 2.dp)
				)
			}
		}
	}
}

@Composable
fun FavoriteBusinessListCard(
	business: FavoriteBusiness,
	modifier: Modifier = Modifier,
	onClick: () -> Unit
) {
	Card(
		modifier = modifier.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(2.dp)
	) {
		Row(
			modifier = Modifier
				.fillMaxWidth()
				.padding(12.dp)
		) {
			Box {
				Image(
					painter = painterResource(id = business.imageRes),
					contentDescription = business.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.size(80.dp)
						.clip(RoundedCornerShape(8.dp))
				)
				
				Surface(
					modifier = Modifier
						.align(Alignment.TopEnd)
						.padding(4.dp),
					shape = CircleShape,
					color = Color.Red
				) {
					Icon(
						Icons.Default.Favorite,
						contentDescription = "Favorito",
						tint = Color.White,
						modifier = Modifier.padding(3.dp).size(10.dp)
					)
				}
			}
			
			Spacer(modifier = Modifier.width(12.dp))
			
			Column(
				modifier = Modifier.weight(1f)
			) {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					Text(
						text = business.name,
						style = MaterialTheme.typography.titleSmall,
						fontWeight = FontWeight.Bold,
						modifier = Modifier.weight(1f)
					)
					
					if (business.badge.isNotEmpty()) {
						Surface(
							shape = RoundedCornerShape(6.dp),
							color = Color(0xFFE91E63).copy(alpha = 0.1f)
						) {
							Text(
								text = business.badge,
								color = Color(0xFFE91E63),
								fontSize = 8.sp,
								fontWeight = FontWeight.Bold,
								modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
							)
						}
					}
				}
				
				Text(
					text = business.category,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				
				Text(
					text = business.specialNote,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 11.sp,
					color = MaterialTheme.colorScheme.primary,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				
				Spacer(modifier = Modifier.height(4.dp))
				
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							Icons.Default.Star,
							contentDescription = "Rating",
							tint = Color(0xFFFFB800),
							modifier = Modifier.size(14.dp)
						)
						Text(
							text = "${business.rating} (${business.reviewCount})",
							style = MaterialTheme.typography.bodySmall,
							fontSize = 11.sp,
							modifier = Modifier.padding(start = 2.dp)
						)
						
						Spacer(modifier = Modifier.width(8.dp))
						
						Icon(
							Icons.Default.AccessTime,
							contentDescription = "Tiempo",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(12.dp)
						)
						Text(
							text = business.deliveryTime,
							style = MaterialTheme.typography.bodySmall,
							fontSize = 11.sp,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.padding(start = 2.dp)
						)
					}
					
					Text(
						text = if (business.deliveryPrice == "GRATIS") "Envío gratis" else business.deliveryPrice,
						style = MaterialTheme.typography.bodySmall,
						fontSize = 10.sp,
						color = if (business.deliveryPrice == "GRATIS") Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurfaceVariant,
						fontWeight = FontWeight.Medium
					)
				}
				
				Text(
					text = business.schedule,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 10.sp,
					color = if (business.schedule.contains("Abierto")) Color(0xFF4CAF50) else Color(0xFFE91E63),
					modifier = Modifier.padding(top = 2.dp)
				)
			}
		}
	}
}





@Composable
fun SectionHeader(
	title: String,
	subtitle: String,
	icon: ImageVector? = null,
	onSeeAllClick: () -> Unit
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 8.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Row(verticalAlignment = Alignment.CenterVertically) {
			if (icon != null) {
				Icon(
					icon,
					contentDescription = null,
					tint = MaterialTheme.colorScheme.primary,
					modifier = Modifier.size(20.dp)
				)
				Spacer(modifier = Modifier.width(8.dp))
			}
			Column {
				Text(
					text = title,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold
				)
				Text(
					text = subtitle,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
		TextButton(onClick = onSeeAllClick) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Text("Ver todo", fontSize = 12.sp)
				Icon(
					Icons.Default.ArrowForward,
					contentDescription = "Ver todo",
					modifier = Modifier.size(14.dp)
				)
			}
		}
	}
}

// Data classes para la pantalla de favoritos
data class FavoriteBusiness(
	val name: String,
	val category: String,
	val imageRes: Int,
	val rating: Float,
	val deliveryTime: String,
	val isFavorite: Boolean,
	val deliveryPrice: String,
	val badge: String,
	val reviewCount: Int,
	val schedule: String,
	val specialNote: String
)

data class FavoriteDish(
	val name: String,
	val restaurant: String,
	val price: String,
	val imageRes: Int,
	val rating: Float,
	val description: String,
	val orderCount: Int
)

data class QuickOrder(
	val name: String,
	val description: String,
	val price: String,
	val restaurant: String,
	val lastOrder: String
)

data class ActionItem(
	val title: String,
	val icon: ImageVector,
	val color: Color
)









//@Preview(showBackground = true)
//@Composable
//fun MenuScreenPreview() {
//
//	val navController = rememberNavController()
//	MenuScreenContent(navController = navController)
//}