package com.techcode.palplato.presentation.ui.main

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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.viewmodels.auth.BusinessViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
	navController: NavController,
) {
	MainScreenContent(
		navController = navController
	)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(
	navController: NavController,
	viewModel: BusinessViewModel = hiltViewModel()
) {
	val businesses by viewModel.businesses.collectAsState()
	
	var selectedCategory by remember { mutableStateOf("Todos") }
	var currentOfferIndex by remember { mutableStateOf(0) }
	
	
	val categorias = listOf(
		Category("Todos", com.techcode.palplato.R.drawable.ic_hamburguesa, true),
		Category("Hamburguesas", com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Pizza", com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Sushi", com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Parrilla",com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Vegetariano", com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Postres", com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Bebidas",com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Desayuno", com.techcode.palplato.R.drawable.ic_hamburguesa, false),
		Category("Saludable", com.techcode.palplato.R.drawable.ic_hamburguesa, false)
	)
	
	val ofertas = listOf(
		Offer("50% OFF", "En tu primer pedido", "Válido hasta mañana", Color(0xFFE91E63)),
		Offer("Envío GRATIS", "Pedidos +$25.000", "Solo hoy", Color(0xFF4CAF50)),
		Offer("2x1", "En pizzas medianas", "Fines de semana", Color(0xFFFF9800))
	)
	
	val negociosDestacados = listOf(
		Business1("Burger House", "Hamburguesas", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "15-25 min", true, "$3.500", "Promoción 2x1", true),
		Business1("Pizzería Donato", "Pizza", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.6f, "20-30 min", false, "$2.000", "Nuevo", false),
		Business1("SushiGo", "Sushi", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.9f, "25-35 min", true, "GRATIS", "Premium", true),
		Business1("Taco Bell", "Mexicana", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.7f, "18-28 min", false, "$4.000", "Popular", false)
	)
	
	val negociosCercanos = listOf(
		Business1("La Parrilla", "Parrilla", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.7f, "10-20 min", false, "$3.000", "", false),
		Business1("Veggie Life", "Vegetariano", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.5f, "15-25 min", false, "$2.500", "", false),
		Business1("Café Central", "Café", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "5-15 min", false, "GRATIS", "", false),
		Business1("Donut King", "Postres", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.6f, "12-22 min", true, "$1.500", "", false),
		Business1("Juice Bar", "Bebidas",com.techcode.palplato.R.drawable.ic_hamburguesa, 4.4f, "8-18 min", false, "$2.000", "", false),
		Business1("Morning Bite", "Desayuno",com.techcode.palplato.R.drawable.ic_hamburguesa, 4.9f, "10-15 min", false, "$3.500", "", false)
	)
	
	val platosPopulares = listOf(
		Dish("Big Mac Deluxe", "Burger House", "$18.900", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f, "La clásica con doble carne"),
		Dish("Pizza Margherita", "Pizzería Donato", "$24.500", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.6f, "Tomate, mozzarella y albahaca"),
		Dish("Salmon Roll", "SushiGo", "$15.900", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.9f, "Salmón fresco con aguacate"),
		Dish("Tacos al Pastor", "Taco Bell", "$16.500", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.7f, "3 tacos con piña y cilantro")
	)
	
	val cuponesEspeciales = listOf(
		Coupon("PRIMERA50", "50% OFF en tu primer pedido", "Válido por 24 horas", Color(0xFFE91E63)),
		Coupon("GRATIS25", "Envío gratis en pedidos +$25k", "Aplicar automáticamente", Color(0xFF4CAF50)),
		Coupon("COMBO2X1", "2x1 en combos seleccionados", "Solo fines de semana", Color(0xFFFF9800)),
		Coupon("STUDENT20", "20% descuento estudiantes", "Con credencial vigente", Color(0xFF2196F3))
	)
	
	val negociosRapidos = listOf(
		QuickService("McDonald's", "5-12 min", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.8f),
		QuickService("KFC", "8-15 min", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.6f),
		QuickService("Subway", "3-10 min", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.7f),
		QuickService("Domino's", "15-25 min", com.techcode.palplato.R.drawable.ic_hamburguesa, 4.5f)
	)
	
	
	// Auto-scroll para ofertas
	LaunchedEffect(Unit) {
		while (true) {
			delay(4000)
			currentOfferIndex = (currentOfferIndex + 1) % 3
		}
	}
	
	Scaffold(
		topBar = {
			TopAppBar(
				title = {
					Column {
						Text(
							"Pa'l Plato",
							style = MaterialTheme.typography.titleLarge,
							fontWeight = FontWeight.Bold
						)
					}
				},
				navigationIcon = {
					Box {
						IconButton(onClick = { /* Perfil */ }) {
							Icon(
								painter = painterResource(id = com.techcode.palplato.R.drawable.ic_user),
								contentDescription = "Perfil",
								modifier = Modifier.size(24.dp)
							)
						}
						Surface(
							modifier = Modifier
								.size(8.dp)
								.align(Alignment.TopEnd)
								.offset(x = (-4).dp, y = 4.dp),
							shape = CircleShape,
							color = Color.Red
						) {}
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
		bottomBar = {
			BottomNavigationBar(navController)
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(20.dp)
		) {
			// Ofertas carousel automático
			item { OfertasCarousel(ofertas, currentOfferIndex) }
			
			// Barra de búsqueda con filtros
			item { SearchBarWithFilters(modifier = Modifier.padding(horizontal = 16.dp)) }
			
//			// Categorías horizontales
//			item {
//				Column {
//					Text(
//						text = "Categorías",
//						style = MaterialTheme.typography.titleMedium,
//						fontWeight = FontWeight.Bold,
//						modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
//					)
//					LazyRow(
//						modifier = Modifier.fillMaxWidth(),
//						contentPadding = PaddingValues(horizontal = 16.dp),
//						horizontalArrangement = Arrangement.spacedBy(12.dp)
//					) {
//						items(categorias) { categoria ->
//							CategoryChip(
//								category = categoria,
//								isSelected = categoria.name == selectedCategory,
//								onClick = { selectedCategory = categoria.name }
//							)
//						}
//					}
//				}
//			}
			
//			// Entrega rápida (Mock existente)
//			item {
//				Column {
//					SectionHeader(
//						title = "Entrega Express",
//						subtitle = "Menos de 15 minutos",
//						icon = Icons.Default.FlashOn,
//						onSeeAllClick = { /* Ver todos */ }
//					)
//					LazyRow(
//						modifier = Modifier.fillMaxWidth(),
//						contentPadding = PaddingValues(horizontal = 16.dp),
//						horizontalArrangement = Arrangement.spacedBy(12.dp)
//					) {
//						items(negociosRapidos) { negocio ->
//							QuickServiceCard(negocio) { /* Navegación */ }
//						}
//					}
//				}
//			}
//
			// Platos populares (Mock existente)
			item {
				Column {
					SectionHeader(
						title = "Platos Populares",
						subtitle = "Los más pedidos hoy",
						icon = Icons.Default.TrendingUp,
						onSeeAllClick = { /* Ver todos */ }
					)
					LazyRow(
						modifier = Modifier.fillMaxWidth(),
						contentPadding = PaddingValues(horizontal = 16.dp),
						horizontalArrangement = Arrangement.spacedBy(16.dp)
					) {
						items(platosPopulares) { plato ->
							PopularDishCard(plato) { /* Agregar al carrito */ }
						}
					}
				}
			}
			
			// LOCALES REALES DESDE FIRESTORE
			item {
				Column {
					SectionHeader(
						title = "Locales",
						subtitle = "Los más populares",
						icon = Icons.Default.Star,
						isOpen = businesses.any { it.isOpen }, // ✅ Mostramos abierto si hay al menos 1 abierto
						onSeeAllClick = {
							navController.navigate(AppRoutes.AllBusinessesScreen)
						}
					)
					
					LazyRow(
						modifier = Modifier.fillMaxWidth(),
						contentPadding = PaddingValues(horizontal = 16.dp),
						horizontalArrangement = Arrangement.spacedBy(16.dp)
					) {
						val limitedBusinesses = businesses.take(10) // ✅ Solo mostramos 10
						items(limitedBusinesses) { business ->
							BusinessCard(business) {
								navController.navigate(AppRoutes.GetAllProductsBusinessScreen(business.id))
							}
						}
					}
				}
			}
			
			
			
			// Cercanos a ti (Mock existente)
			item {
				Column {
					SectionHeader(
						title = "Cerca de ti",
						subtitle = "Entrega rápida",
						icon = Icons.Default.NearMe,
						onSeeAllClick = { /* Ver todos */ }
					)
					LazyRow(
						modifier = Modifier.fillMaxWidth(),
						contentPadding = PaddingValues(horizontal = 16.dp),
						horizontalArrangement = Arrangement.spacedBy(12.dp)
					) {
						items(negociosCercanos) { negocio ->
							CompactBusinessCard(negocio) { /* Navegación */ }
						}
					}
				}
			}
			
			// Footer (Mock existente)
			item { FooterInfo() }
			
			// Espacio final
			item { Spacer(modifier = Modifier.height(16.dp)) }
		}
	}
}


@Composable
fun BusinessCard(
	business: Business,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(240.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(16.dp),
		elevation = CardDefaults.cardElevation(6.dp)
	) {
		Column {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(150.dp)
			) {
				// Imagen del negocio
				AsyncImage(
					model = business.logoUrl ?: "",
					contentDescription = business.name,
					modifier = Modifier.fillMaxSize(),
					contentScale = ContentScale.Crop
				)
				
				// Gradiente para mejor visibilidad del texto
				Box(
					modifier = Modifier
						.fillMaxSize()
						.background(
							Brush.verticalGradient(
								listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
							)
						)
				)
				
				// Estado del negocio
				Card(
					modifier = Modifier
						.padding(8.dp)
						.align(Alignment.TopEnd),
					colors = CardDefaults.cardColors(
						containerColor = when {
							business.isOpen -> Color(0xFF4CAF50) // Abierto
							else -> Color(0xFFF44336) // Cerrado
						}
					),
					shape = RoundedCornerShape(20.dp)
				) {
					Text(
						text = if (business.isOpen) "Abierto" else "Cerrado",
						color = Color.White,
						style = MaterialTheme.typography.labelSmall,
						fontWeight = FontWeight.Bold,
						modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
					)
				}
				
				// Nombre del negocio sobre la imagen
				Text(
					text = business.name,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					color = Color.White,
					modifier = Modifier
						.align(Alignment.BottomStart)
						.padding(12.dp),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
			}
			
			// Información adicional
			Column(modifier = Modifier.padding(12.dp)) {
				// Descripción breve
				if (business.description.isNotEmpty()) {
					Text(
						text = business.description,
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)
					Spacer(modifier = Modifier.height(6.dp))
				}
				
				// Categorías
				if (business.categories.isNotEmpty()) {
					LazyRow(
						horizontalArrangement = Arrangement.spacedBy(6.dp)
					) {
						items(business.categories.take(2)) { category ->
							Card(
								colors = CardDefaults.cardColors(
									containerColor = MaterialTheme.colorScheme.primaryContainer
								),
								shape = RoundedCornerShape(12.dp)
							) {
								Text(
									text = category.name,
									style = MaterialTheme.typography.labelSmall,
									color = MaterialTheme.colorScheme.onPrimaryContainer,
									modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
								)
							}
						}
						
						if (business.categories.size > 2) {
							item {
								Text(
									text = "+${business.categories.size - 2}",
									style = MaterialTheme.typography.labelSmall,
									color = MaterialTheme.colorScheme.primary,
									fontWeight = FontWeight.Bold,
									modifier = Modifier
										.padding(horizontal = 4.dp)
								)
							}
						}
					}
				}
				
				Spacer(modifier = Modifier.height(6.dp))
			}
		}
	}
}



@Composable
fun OfertasCarousel(ofertas: List<Offer>, currentIndex: Int) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		shape = RoundedCornerShape(16.dp)
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.height(120.dp)
				.background(
					brush = Brush.horizontalGradient(
						colors = listOf(
							ofertas[currentIndex].color,
							ofertas[currentIndex].color.copy(alpha = 0.8f)
						)
					)
				)
				.padding(16.dp)
		) {
			Row(
				modifier = Modifier.fillMaxSize(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Column(modifier = Modifier.weight(1f)) {
					Text(
						text = ofertas[currentIndex].title,
						style = MaterialTheme.typography.headlineSmall,
						fontWeight = FontWeight.Bold,
						color = Color.White
					)
					Text(
						text = ofertas[currentIndex].description,
						style = MaterialTheme.typography.bodyMedium,
						color = Color.White.copy(alpha = 0.9f)
					)
					Text(
						text = ofertas[currentIndex].validity,
						style = MaterialTheme.typography.bodySmall,
						color = Color.White.copy(alpha = 0.7f)
					)
				}
				
				Button(
					onClick = { /* Usar oferta */ },
					colors = ButtonDefaults.buttonColors(
						containerColor = Color.White,
						contentColor = ofertas[currentIndex].color
					),
					shape = RoundedCornerShape(20.dp)
				) {
					Text("Usar ahora", fontWeight = FontWeight.Bold)
				}
			}
			
			// Indicadores
			Row(
				modifier = Modifier
					.align(Alignment.BottomCenter)
					.padding(bottom = 8.dp),
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				ofertas.forEachIndexed { index, _ ->
					Surface(
						modifier = Modifier.size(if (index == currentIndex) 8.dp else 6.dp),
						shape = CircleShape,
						color = if (index == currentIndex) Color.White else Color.White.copy(alpha = 0.5f)
					) {}
				}
			}
		}
	}
}

@Composable
fun SearchBarWithFilters(modifier: Modifier = Modifier) {
	Column(modifier = modifier) {
		OutlinedTextField(
			value = "",
			onValueChange = { },
			placeholder = { Text("Buscar restaurantes, platos, ingredientes...") },
			leadingIcon = {
				Icon(
					Icons.Default.Search,
					contentDescription = "Buscar",
					tint = MaterialTheme.colorScheme.onSurfaceVariant
				)
			},
			trailingIcon = {
				Row {
					IconButton(onClick = { /* Filtros */ }) {
						Icon(
							Icons.Default.Tune,
							contentDescription = "Filtros",
							tint = MaterialTheme.colorScheme.primary
						)
					}
					IconButton(onClick = { /* Buscar por voz */ }) {
						Icon(
							Icons.Default.Mic,
							contentDescription = "Buscar por voz",
							tint = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
				}
			},
			modifier = Modifier
				.fillMaxWidth()
				.clickable { /* Abrir búsqueda */ },
			enabled = false,
			shape = RoundedCornerShape(24.dp),
			colors = OutlinedTextFieldDefaults.colors(
				disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
			)
		)
		
	}
}




@Composable
fun QuickServiceCard(
	service: QuickService,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(100.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(2.dp)
	) {
		Column(
			modifier = Modifier.padding(8.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Image(
				painter = painterResource(id = service.logoRes),
				contentDescription = service.name,
				modifier = Modifier
					.size(40.dp)
					.clip(CircleShape)
			)
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = service.name,
				style = MaterialTheme.typography.bodySmall,
				fontWeight = FontWeight.Medium,
				textAlign = TextAlign.Center,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
			Text(
				text = service.deliveryTime,
				style = MaterialTheme.typography.bodySmall,
				fontSize = 10.sp,
				color = Color(0xFF4CAF50),
				textAlign = TextAlign.Center
			)
		}
	}
}

@Composable
fun CouponCard(
	coupon: Coupon,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(200.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = coupon.color.copy(alpha = 0.1f)
		),
		border = BorderStroke(1.dp, coupon.color.copy(alpha = 0.3f))
	) {
		Column(
			modifier = Modifier.padding(12.dp)
		) {
			Row(
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.Top,
				modifier = Modifier.fillMaxWidth()
			) {
				Text(
					text = coupon.code,
					style = MaterialTheme.typography.bodyMedium,
					fontWeight = FontWeight.Bold,
					color = coupon.color
				)
				Surface(
					shape = RoundedCornerShape(4.dp),
					color = coupon.color
				) {
					Icon(
						Icons.Default.ContentCopy,
						contentDescription = "Copiar",
						tint = Color.White,
						modifier = Modifier
							.size(16.dp)
							.padding(2.dp)
					)
				}
			}
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = coupon.description,
				style = MaterialTheme.typography.bodySmall,
				fontWeight = FontWeight.Medium,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis
			)
			Text(
				text = coupon.validity,
				style = MaterialTheme.typography.bodySmall,
				fontSize = 10.sp,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
		}
	}
}

@Composable
fun PopularDishCard(
	dish: Dish,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(180.dp)
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
						.height(100.dp)
				)
				
				Surface(
					modifier = Modifier
						.align(Alignment.TopEnd)
						.padding(8.dp),
					shape = RoundedCornerShape(12.dp),
					color = Color.Black.copy(alpha = 0.7f)
				) {
					Row(
						verticalAlignment = Alignment.CenterVertically,
						modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
					) {
						Icon(
							Icons.Default.Star,
							contentDescription = "Rating",
							tint = Color(0xFFFFB800),
							modifier = Modifier.size(12.dp)
						)
						Text(
							text = dish.rating.toString(),
							color = Color.White,
							fontSize = 10.sp,
							fontWeight = FontWeight.Bold
						)
					}
				}
			}
			
			Column(modifier = Modifier.padding(8.dp)) {
				Text(
					text = dish.name,
					style = MaterialTheme.typography.bodyMedium,
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = dish.restaurant,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					fontSize = 10.sp
				)
				Text(
					text = dish.description,
					style = MaterialTheme.typography.bodySmall,
					fontSize = 10.sp,
					maxLines = 2,
					overflow = TextOverflow.Ellipsis,
					modifier = Modifier.padding(vertical = 2.dp)
				)
				
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = dish.price,
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.primary
					)
					Surface(
						shape = CircleShape,
						color = MaterialTheme.colorScheme.primary,
						modifier = Modifier.size(24.dp)
					) {
						IconButton(
							onClick = onClick,
							modifier = Modifier.size(24.dp)
						) {
							Icon(
								Icons.Default.Add,
								contentDescription = "Agregar",
								tint = Color.White,
								modifier = Modifier.size(12.dp)
							)
						}
					}
				}
			}
		}
	}
}


@Composable
fun RecommendedItem(
	title: String,
	subtitle: String,
	price: String,
	discount: String,
	modifier: Modifier = Modifier
) {
	Card(
		modifier = modifier.clickable { /* Acción */ },
		shape = RoundedCornerShape(12.dp),
		colors = CardDefaults.cardColors(
			containerColor = Color.White
		)
	) {
		Column(
			modifier = Modifier.padding(12.dp)
		) {
			Surface(
				shape = RoundedCornerShape(6.dp),
				color = if (discount.contains("OFF")) Color(0xFFE91E63) else Color(0xFF4CAF50)
			) {
				Text(
					text = discount,
					color = Color.White,
					fontSize = 10.sp,
					fontWeight = FontWeight.Bold,
					modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
				)
			}
			Spacer(modifier = Modifier.height(4.dp))
			Text(
				text = title,
				style = MaterialTheme.typography.bodySmall,
				fontWeight = FontWeight.Bold,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
			Text(
				text = subtitle,
				style = MaterialTheme.typography.bodySmall,
				fontSize = 10.sp,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis
			)
			Text(
				text = price,
				style = MaterialTheme.typography.bodySmall,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.primary
			)
		}
	}
}




@Composable
fun FooterInfo() {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		shape = RoundedCornerShape(16.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
		)
	) {
		Column(
			modifier = Modifier.padding(16.dp),
			horizontalAlignment = Alignment.CenterHorizontally
		) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				FooterItem(
					icon = Icons.Default.DeliveryDining,
					title = "Entrega",
					subtitle = "15-30 min"
				)
				FooterItem(
					icon = Icons.Default.Payment,
					title = "Pago",
					subtitle = "Seguro"
				)
				FooterItem(
					icon = Icons.Default.SupportAgent,
					title = "Soporte",
					subtitle = "24/7"
				)
				FooterItem(
					icon = Icons.Default.Verified,
					title = "Calidad",
					subtitle = "Garantizada"
				)
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			Divider(
				color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.2f),
				thickness = 0.5.dp
			)
			
			Spacer(modifier = Modifier.height(12.dp))
			
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceEvenly
			) {
				TextButton(onClick = { /* Ayuda */ }) {
					Text("Ayuda", fontSize = 12.sp)
				}
				TextButton(onClick = { /* Términos */ }) {
					Text("Términos", fontSize = 12.sp)
				}
				TextButton(onClick = { /* Privacidad */ }) {
					Text("Privacidad", fontSize = 12.sp)
				}
				TextButton(onClick = { /* Contacto */ }) {
					Text("Contacto", fontSize = 12.sp)
				}
			}
			
			Text(
				"Pa'l Plato © 2024 - Colombia",
				style = MaterialTheme.typography.bodySmall,
				fontSize = 10.sp,
				color = MaterialTheme.colorScheme.onSurfaceVariant,
				textAlign = TextAlign.Center
			)
		}
	}
}

@Composable
fun FooterItem(
	icon: ImageVector,
	title: String,
	subtitle: String
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			icon,
			contentDescription = title,
			tint = MaterialTheme.colorScheme.primary,
			modifier = Modifier.size(20.dp)
		)
		Text(
			text = title,
			style = MaterialTheme.typography.bodySmall,
			fontWeight = FontWeight.Medium,
			fontSize = 10.sp
		)
		Text(
			text = subtitle,
			style = MaterialTheme.typography.bodySmall,
			fontSize = 9.sp,
			color = MaterialTheme.colorScheme.onSurfaceVariant
		)
	}
}

@Composable
fun CategoryChip(
	category: Category,
	isSelected: Boolean,
	onClick: () -> Unit
) {
	FilterChip(
		onClick = onClick,
		label = {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(6.dp)
			) {
				Icon(
					painter = painterResource(id = category.iconRes),
					contentDescription = null,
					modifier = Modifier.size(16.dp)
				)
				Text(
					text = category.name,
					fontSize = 12.sp
				)
			}
		},
		selected = isSelected,
		colors = FilterChipDefaults.filterChipColors(
			selectedContainerColor = MaterialTheme.colorScheme.primary,
			selectedLabelColor = MaterialTheme.colorScheme.onPrimary
		)
	)
}

@Composable
fun SectionHeader(
	title: String,
	subtitle: String,
	icon: ImageVector? = null,
	onSeeAllClick: () -> Unit,
	isOpen: Boolean? = null // ← nuevo parámetro opcional
) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp, vertical = 12.dp),
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
				Row(verticalAlignment = Alignment.CenterVertically) {
					Text(
						text = title,
						style = MaterialTheme.typography.titleMedium,
						fontWeight = FontWeight.Bold
					)
					
					// Estado abierto/cerrado
					if (isOpen != null) {
						Spacer(modifier = Modifier.width(8.dp))
						Card(
							colors = CardDefaults.cardColors(
								containerColor = if (isOpen) Color(0xFF4CAF50) else Color(0xFFF44336)
							),
							shape = RoundedCornerShape(20.dp),
							elevation = CardDefaults.cardElevation(0.dp)
						) {
							Text(
								text = if (isOpen) "Abierto" else "Cerrado",
								style = MaterialTheme.typography.labelSmall,
								color = Color.White,
								modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
								fontWeight = FontWeight.Bold
							)
						}
					}
				}
				
				Text(
					text = subtitle,
					style = MaterialTheme.typography.bodySmall,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
		
		TextButton(onClick = onSeeAllClick) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Text("Ver todo")
				Icon(
					Icons.Default.ArrowForward,
					contentDescription = "Ver todo",
					modifier = Modifier.size(16.dp)
				)
			}
		}
	}
}


@Composable
fun QuickActionButton(
	action: QuickAction,
	onClick: () -> Unit
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier.clickable { onClick() }
	) {
		Surface(
			shape = CircleShape,
			color = action.color.copy(alpha = 0.1f),
			modifier = Modifier.size(56.dp)
		) {
			Icon(
				action.icon,
				contentDescription = action.title,
				tint = action.color,
				modifier = Modifier
					.size(24.dp)
					.padding(16.dp)
			)
		}
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = action.title,
			style = MaterialTheme.typography.bodySmall,
			fontSize = 10.sp,
			textAlign = TextAlign.Center,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis
		)
	}
}

@Composable
fun FeaturedBusinessCard(
	negocio: Business1,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(280.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(16.dp),
		elevation = CardDefaults.cardElevation(4.dp)
	) {
		Column {
			Box {
				Image(
					painter = painterResource(id = negocio.imageRes),
					contentDescription = negocio.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.fillMaxWidth()
						.height(140.dp)
				)
				
				// Badges
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(8.dp),
					horizontalArrangement = Arrangement.SpaceBetween
				) {
					if (negocio.badge.isNotEmpty()) {
						Surface(
							shape = RoundedCornerShape(12.dp),
							color = when (negocio.badge) {
								"Promoción 2x1" -> Color(0xFFE91E63)
								"Nuevo" -> Color(0xFF4CAF50)
								"Premium" -> Color(0xFFFF9800)
								"Popular" -> Color(0xFF2196F3)
								else -> Color.Black
							}.copy(alpha = 0.9f)
						) {
							Text(
								text = negocio.badge,
								color = Color.White,
								fontSize = 10.sp,
								fontWeight = FontWeight.Bold,
								modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
							)
						}
					} else {
						Spacer(modifier = Modifier)
					}
					
					if (negocio.isFavorite) {
						Surface(
							shape = CircleShape,
							color = Color.White.copy(alpha = 0.9f)
						) {
							Icon(
								Icons.Default.Favorite,
								contentDescription = "Favorito",
								tint = Color.Red,
								modifier = Modifier.padding(6.dp).size(16.dp)
							)
						}
					}
				}
				
				// Precio de envío
				Surface(
					modifier = Modifier
						.align(Alignment.BottomStart)
						.padding(8.dp),
					shape = RoundedCornerShape(8.dp),
					color = Color.Black.copy(alpha = 0.7f)
				) {
					Text(
						text = if (negocio.deliveryPrice == "GRATIS") "🚚 GRATIS" else "🚚 ${negocio.deliveryPrice}",
						color = if (negocio.deliveryPrice == "GRATIS") Color(0xFF4CAF50) else Color.White,
						fontSize = 10.sp,
						fontWeight = FontWeight.Bold,
						modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
					)
				}
			}
			
			Column(modifier = Modifier.padding(12.dp)) {
				Text(
					text = negocio.name,
					style = MaterialTheme.typography.titleMedium,
					fontWeight = FontWeight.Bold,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = negocio.category,
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
				
				Spacer(modifier = Modifier.height(8.dp))
				
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
							modifier = Modifier.size(16.dp)
						)
						Text(
							text = negocio.rating.toString(),
							style = MaterialTheme.typography.bodySmall,
							fontWeight = FontWeight.Medium,
							modifier = Modifier.padding(start = 4.dp)
						)
						Text(
							text = " (500+)",
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							fontSize = 10.sp
						)
					}
					
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							Icons.Default.AccessTime,
							contentDescription = "Tiempo",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(14.dp)
						)
						Text(
							text = negocio.deliveryTime,
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.padding(start = 4.dp)
						)
					}
				}
			}
		}
	}
}

@Composable
fun CompactBusinessCard(
	negocio: Business1,
	onClick: () -> Unit
) {
	Card(
		modifier = Modifier
			.width(160.dp)
			.clickable { onClick() },
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(2.dp)
	) {
		Column {
			Box {
				Image(
					painter = painterResource(id = negocio.imageRes),
					contentDescription = negocio.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.fillMaxWidth()
						.height(80.dp)
				)
				
				if (negocio.isFavorite) {
					Surface(
						modifier = Modifier
							.align(Alignment.TopEnd)
							.padding(4.dp),
						shape = CircleShape,
						color = Color.White.copy(alpha = 0.9f)
					) {
						Icon(
							Icons.Default.Favorite,
							contentDescription = "Favorito",
							tint = Color.Red,
							modifier = Modifier.padding(3.dp).size(12.dp)
						)
					}
				}
			}
			
			Column(modifier = Modifier.padding(8.dp)) {
				Text(
					text = negocio.name,
					style = MaterialTheme.typography.bodyMedium,
					fontWeight = FontWeight.Medium,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				
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
							text = negocio.rating.toString(),
							style = MaterialTheme.typography.bodySmall,
							fontSize = 10.sp
						)
					}
					
					Text(
						text = negocio.deliveryTime.split("-")[0] + " min",
						style = MaterialTheme.typography.bodySmall,
						fontSize = 10.sp,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
				
				Text(
					text = if (negocio.deliveryPrice == "GRATIS") "Envío gratis" else "Envío ${negocio.deliveryPrice}",
					style = MaterialTheme.typography.bodySmall,
					fontSize = 9.sp,
					color = if (negocio.deliveryPrice == "GRATIS") Color(0xFF4CAF50) else MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
	}
}

// Data classes actualizadas y nuevas
data class Business1(
	val name: String,
	val category: String,
	val imageRes: Int,
	val rating: Float,
	val deliveryTime: String,
	val isFavorite: Boolean,
	val deliveryPrice: String,
	val badge: String,
	val isPremium: Boolean
)

data class Category(
	val name: String,
	val iconRes: Int,
	val isSelected: Boolean
)

data class Offer(
	val title: String,
	val description: String,
	val validity: String,
	val color: Color
)

data class Dish(
	val name: String,
	val restaurant: String,
	val price: String,
	val imageRes: Int,
	val rating: Float,
	val description: String
)

data class Coupon(
	val code: String,
	val description: String,
	val validity: String,
	val color: Color
)

data class QuickService(
	val name: String,
	val deliveryTime: String,
	val logoRes: Int,
	val rating: Float
)

data class QuickAction(
	val title: String,
	val icon: ImageVector,
	val color: Color
)

data class BlogPost(
	val title: String,
	val summary: String,
	val imageRes: Int
)

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//
//	val navController = rememberNavController()
//	MainScreenContent(navController = navController)
//}