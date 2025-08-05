package com.techcode.palplato.presentation.ui.bussines

import android.R
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.viewmodels.auth.BusinessViewModel
import com.techcode.palplato.domain.viewmodels.auth.ProductViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes

@Composable
fun GetAllProductsBusinessScreen(
	navController: NavController,
	businessId: String,
	productsViewModel: ProductViewModel = hiltViewModel(),
	businessViewModel: BusinessViewModel = hiltViewModel(),
) {
	val products by productsViewModel.products.collectAsState()
	val business by businessViewModel.fetchBusinessById(businessId).collectAsState(initial = null)
	
	LaunchedEffect(businessId) {
		productsViewModel.loadProducts(businessId)
	}
	
	when {
		business == null -> {
			// Estado de carga
			Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
				CircularProgressIndicator()
			}
		}
		
		else -> {
			GetAllProductsBusinessScreenContent(
				navController = navController,
				business = business!!,
				products = products,
				onMoreInfoClick = { /* Mostrar detalles o diálogo con dirección, teléfono y horarios */ },
				onAddToCartClick = { product -> println("Producto agregado: ${product.name}") }
			)
		}
	}
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GetAllProductsBusinessScreenContent(
	navController: NavController,
	business: Business,
	products: List<Product>,
	onMoreInfoClick: () -> Unit,
	onAddToCartClick: (Product) -> Unit,
) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = { Text("Menú", style = MaterialTheme.typography.titleMedium) },
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
					IconButton(onClick = { navController.navigate(AppRoutes.cartScreen) }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_cart),
							contentDescription = "Carrito",
							modifier = Modifier.size(24.dp)
						)
					}
				}
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
		) {
			
			BusinessHeader(
				navController = navController,
				business = business
			)
			if (products.isEmpty()) {
				Box(
					modifier = Modifier.fillMaxSize(),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = "No hay productos disponibles",
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			} else {
				LazyVerticalGrid(
					columns = GridCells.Fixed(2),
					modifier = Modifier
						.fillMaxSize()
						.padding(8.dp),
					contentPadding = PaddingValues(8.dp),
					verticalArrangement = Arrangement.spacedBy(12.dp),
					horizontalArrangement = Arrangement.spacedBy(12.dp)
				) {
					items(products) { product ->
						ProductItem(
							product = product,
							onAddClick = { onAddToCartClick(product) },
							onClick = {
								navController.navigate(
									AppRoutes.ProductDetailScreen(
										businessId = business.id,
										productId = product.id
									)
								)
							}
						)
					}
				}
				
			}
		}
	}
}


@Composable
fun BusinessHeader(
	navController: NavController,
	business: Business,
	modifier: Modifier = Modifier,
) {
	Card(
		modifier = modifier.fillMaxWidth(),
		elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(16.dp)
		) {
			// Header principal con logo, nombre y botón
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.SpaceBetween,
				modifier = Modifier.fillMaxWidth()
			) {
				// Información principal del negocio
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier.weight(1f)
				) {
					// Logo del negocio
					if (!business.logoUrl.isNullOrEmpty()) {
						AsyncImage(
							model = ImageRequest.Builder(LocalContext.current)
								.data(business.logoUrl)
								.crossfade(true)
								.build(),
							contentDescription = "Logo de ${business.name}",
							contentScale = ContentScale.Crop,
							modifier = Modifier
								.size(56.dp)
								.clip(CircleShape)
								.background(MaterialTheme.colorScheme.surfaceVariant),
							placeholder = painterResource(R.drawable.gallery_thumb),
							error = painterResource(R.drawable.gallery_thumb)
						)
					} else {
						// Placeholder cuando no hay logo
						Box(
							modifier = Modifier
								.size(56.dp)
								.clip(CircleShape)
								.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
							contentAlignment = Alignment.Center
						) {
							Text(
								text = business.name.take(2).uppercase(),
								style = MaterialTheme.typography.titleMedium,
								color = MaterialTheme.colorScheme.primary,
								fontWeight = FontWeight.Bold
							)
						}
					}
					
					Spacer(Modifier.width(12.dp))
					
					// Información principal
					Column {
						Text(
							text = business.name,
							style = MaterialTheme.typography.titleLarge,
							fontWeight = FontWeight.Bold,
							maxLines = 2,
							overflow = TextOverflow.Ellipsis
						)
						
						Spacer(Modifier.height(4.dp))
						
						// Estado del negocio (abierto/cerrado)
						val statusColor = if (business.isOpen) {
							MaterialTheme.colorScheme.primary
						} else {
							MaterialTheme.colorScheme.error
						}
						
						Row(
							verticalAlignment = Alignment.CenterVertically,
							modifier = Modifier
								.background(
									color = statusColor.copy(alpha = 0.1f),
									shape = RoundedCornerShape(12.dp)
								)
								.padding(horizontal = 8.dp, vertical = 4.dp)
						) {
							Canvas(modifier = Modifier.size(8.dp)) {
								drawCircle(color = statusColor)
							}
							
							Spacer(Modifier.width(6.dp))
							
							Text(
								text = if (business.isOpen) "Abierto" else "Cerrado",
								style = MaterialTheme.typography.labelSmall,
								color = statusColor,
								fontWeight = FontWeight.Medium
							)
						}
					}
				}
				
				// Botón más información con navegación directa
				OutlinedButton(
					onClick = {
						navController.navigate(AppRoutes.BusinessDetailScreen(business.id))
					},
					modifier = Modifier.padding(start = 8.dp)
				) {
					Icon(
						imageVector = Icons.Default.Info,
						contentDescription = null,
						modifier = Modifier.size(16.dp)
					)
					Spacer(Modifier.width(4.dp))
					Text("Más info")
				}
			}
			
			// Información de contacto
			Column(modifier = Modifier.padding(top = 12.dp)) {
				// Dirección
				business.direction.takeIf { it.isNotEmpty() }?.let { address ->
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							imageVector = Icons.Default.LocationOn,
							contentDescription = "Dirección",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(16.dp)
						)
						
						Spacer(Modifier.width(8.dp))
						
						Text(
							text = address,
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							maxLines = 2,
							overflow = TextOverflow.Ellipsis
						)
					}
				}
				
				// Teléfono
				business.phone.takeIf { it.isNotEmpty() }?.let { phone ->
					Row(
						verticalAlignment = Alignment.CenterVertically,
						modifier = Modifier.padding(top = 4.dp)
					) {
						Icon(
							imageVector = Icons.Default.Phone,
							contentDescription = "Teléfono",
							tint = MaterialTheme.colorScheme.onSurfaceVariant,
							modifier = Modifier.size(16.dp)
						)
						
						Spacer(Modifier.width(8.dp))
						
						Text(
							text = phone,
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant,
							maxLines = 2,
							overflow = TextOverflow.Ellipsis
						)
					}
				}
			}
		}
	}
}


@Composable
fun ProductItem(
	product: Product,
	onAddClick: () -> Unit,
	onClick: () -> Unit,
) {
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.aspectRatio(0.8f)
			.clickable { onClick() }, // Aquí navegamos al detalle
		shape = RoundedCornerShape(12.dp),
		elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
	) {
		Column(
			modifier = Modifier.fillMaxSize()
		) {
			Box(modifier = Modifier.fillMaxWidth()) {
				AsyncImage(
					model = product.imageUrl,
					contentDescription = product.name,
					contentScale = ContentScale.Crop,
					modifier = Modifier
						.height(120.dp)
						.fillMaxWidth()
				)
				IconButton(
					onClick = onAddClick,
					modifier = Modifier
						.align(Alignment.TopEnd)
						.padding(6.dp)
						.size(28.dp)
						.background(
							color = MaterialTheme.colorScheme.primary,
							shape = CircleShape
						)
				) {
					Icon(
						painter = painterResource(id = com.techcode.palplato.R.drawable.ic_add),
						contentDescription = "Agregar",
						tint = Color.White,
						modifier = Modifier.size(16.dp)
					)
				}
			}
			
			Column(modifier = Modifier.padding(8.dp)) {
				Text(
					text = product.name,
					style = MaterialTheme.typography.bodyMedium,
					fontWeight = FontWeight.Medium,
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
				Text(
					text = "$${product.price}",
					style = MaterialTheme.typography.bodyLarge,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.primary
				)
			}
		}
	}
}




