package com.techcode.palplato.presentation.ui.bussines

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Store
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.data.repository.local.FavoriteBusinessDao
import com.techcode.palplato.data.repository.local.FavoriteBusinessEntity
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.model.BusinessSchedule
import com.techcode.palplato.domain.viewmodels.auth.BusinessViewModel
import com.techcode.palplato.domain.viewmodels.auth.FavoriteBusinessViewModel
import com.techcode.palplato.presentation.navegation.AppRoutes
import java.text.Normalizer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllBusinessesScreen(navController: NavController) {
	
	AllBusinessesScreenContent(navController = navController)
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllBusinessesScreenContent(
	navController: NavController,
	viewModel: BusinessViewModel = hiltViewModel(),
	favViewModel: FavoriteBusinessViewModel = hiltViewModel()
) {
	val businesses by viewModel.businesses.collectAsState()
	val favorites by favViewModel.favorites.collectAsState()
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						"Todos los Negocios",
						style = MaterialTheme.typography.titleLarge,
						fontWeight = FontWeight.Bold
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
					IconButton(onClick = { /* Filtros */ }) {
						Icon(
							imageVector = Icons.Default.FilterList,
							contentDescription = "Filtros",
							modifier = Modifier.size(24.dp)
						)
					}
					IconButton(onClick = { /* Notificaciones */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(24.dp)
						)
					}
				},
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = MaterialTheme.colorScheme.surface
				)
			)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.fillMaxSize()
				.background(MaterialTheme.colorScheme.background)
		) {
			if (businesses.isNotEmpty()) {
				BusinessStatsHeader(businesses = businesses)
			}
			
			if (businesses.isEmpty()) {
				EmptyBusinessesState()
			} else {
				LazyColumn(
					modifier = Modifier.fillMaxSize(),
					contentPadding = PaddingValues(16.dp),
					verticalArrangement = Arrangement.spacedBy(16.dp)
				) {
					items(businesses) { business ->
						val isFavorite = favorites.any { it.businessId == business.id }
						
						EnhancedBusinessListItem(
							business = business,
							isFavorite = isFavorite,
							onClick = {
								navController.navigate(
									AppRoutes.GetAllProductsBusinessScreen(business.id)
								)
							},
							onFavoriteClick = {
								favViewModel.toggleFavorite(business)
							}
						)
					}
					item {
						Spacer(modifier = Modifier.height(16.dp))
					}
				}
			}
		}
	}
}




@Composable
fun BusinessStatsHeader(businesses: List<Business>) {
	val openBusinesses = businesses.count { business -> business.isOpen }
	
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.padding(16.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.primaryContainer
		),
		elevation = CardDefaults.cardElevation(4.dp)
	) {
		Row(
			modifier = Modifier
				.padding(16.dp)
				.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceEvenly
		) {
			StatItem(
				value = businesses.size.toString(),
				label = "Total",
				icon = Icons.Default.Store
			)
			
			Divider(
				modifier = Modifier
					.height(40.dp)
					.width(1.dp),
				color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
			)
			
			StatItem(
				value = openBusinesses.toString(),
				label = "Abiertos",
				icon = Icons.Default.Schedule,
				color = Color(0xFF4CAF50)
			)
			
			Divider(
				modifier = Modifier
					.height(40.dp)
					.width(1.dp),
				color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.3f)
			)
			
			StatItem(
				value = businesses.flatMap { it.categories }
					.distinctBy { it.name }
					.size
					.toString(),
				label = "Categorías",
				icon = Icons.Default.Category
			)
		}
	}
}

@Composable
fun StatItem(
	value: String,
	label: String,
	icon: ImageVector,
	color: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		Icon(
			imageVector = icon,
			contentDescription = null,
			tint = color,
			modifier = Modifier.size(24.dp)
		)
		Spacer(modifier = Modifier.height(4.dp))
		Text(
			text = value,
			style = MaterialTheme.typography.titleLarge,
			fontWeight = FontWeight.Bold,
			color = color
		)
		Text(
			text = label,
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f)
		)
	}
}

@Composable
fun EmptyBusinessesState() {
	Box(
		modifier = Modifier.fillMaxSize(),
		contentAlignment = Alignment.Center
	) {
		Column(
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = Arrangement.Center
		) {
			Icon(
				imageVector = Icons.Default.Store,
				contentDescription = null,
				modifier = Modifier.size(80.dp),
				tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
			)
			Spacer(modifier = Modifier.height(16.dp))
			Text(
				text = "No hay negocios disponibles",
				style = MaterialTheme.typography.titleMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant
			)
			Spacer(modifier = Modifier.height(8.dp))
			Text(
				text = "Los negocios aparecerán aquí cuando estén disponibles",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
				textAlign = TextAlign.Center
			)
		}
	}
}


@Composable
fun EnhancedBusinessListItem(
	business: Business,
	isFavorite: Boolean,
	onClick: () -> Unit,
	onFavoriteClick: () -> Unit,
) {
	val todaySchedule = remember(business) {
		val today = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date()).lowercase()
		business.schedule.find { it.day.lowercase() == today }
	}
	
	Card(
		modifier = Modifier
			.fillMaxWidth()
			.clickable { onClick() },
		shape = RoundedCornerShape(16.dp),
		elevation = CardDefaults.cardElevation(6.dp),
		colors = CardDefaults.cardColors(
			containerColor = MaterialTheme.colorScheme.surface
		)
	) {
		Column {
			Box(
				modifier = Modifier
					.fillMaxWidth()
					.height(120.dp)
			) {
				AsyncImage(
					model = business.logoUrl ?: "",
					contentDescription = business.name,
					modifier = Modifier
						.fillMaxSize()
						.clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)),
					contentScale = ContentScale.Crop,
					placeholder = painterResource(id = android.R.drawable.ic_menu_gallery),
					error = painterResource(id = android.R.drawable.ic_menu_gallery)
				)
				
				// Botón de favorito (con estado desde ViewModel)
				IconButton(
					onClick = onFavoriteClick,
					modifier = Modifier
						.align(Alignment.TopStart)
						.padding(8.dp)
						.size(32.dp)
						.background(
							color = Color.Black.copy(alpha = 0.4f),
							shape = CircleShape
						)
				) {
					Icon(
						imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
						contentDescription = if (isFavorite) "Quitar de favoritos" else "Agregar a favoritos",
						tint = if (isFavorite) Color.Red else Color.White
					)
				}
				
				// Estado del negocio
				Card(
					modifier = Modifier
						.padding(12.dp)
						.align(Alignment.TopEnd),
					colors = CardDefaults.cardColors(
						containerColor = if (business.isOpen) Color(0xFF4CAF50) else Color(0xFFF44336)
					),
					shape = RoundedCornerShape(20.dp)
				) {
					Row(
						modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
						verticalAlignment = Alignment.CenterVertically
					) {
						Icon(
							imageVector = if (business.isOpen) Icons.Default.Schedule else Icons.Default.Close,
							contentDescription = null,
							modifier = Modifier.size(12.dp),
							tint = Color.White
						)
						Spacer(modifier = Modifier.width(4.dp))
						Text(
							text = if (business.isOpen) "Abierto" else "Cerrado",
							style = MaterialTheme.typography.labelSmall,
							color = Color.White,
							fontWeight = FontWeight.Bold
						)
					}
				}
				
				// Nombre del negocio sobre la imagen
				Text(
					text = business.name,
					style = MaterialTheme.typography.titleLarge,
					fontWeight = FontWeight.Bold,
					color = Color.White,
					modifier = Modifier
						.align(Alignment.BottomStart)
						.padding(16.dp),
					maxLines = 1,
					overflow = TextOverflow.Ellipsis
				)
			}
			
			// Resto del contenido...
			Column(modifier = Modifier.padding(16.dp)) {
				if (business.description.isNotEmpty()) {
					Text(
						text = business.description,
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						maxLines = 2,
						overflow = TextOverflow.Ellipsis
					)
					Spacer(modifier = Modifier.height(12.dp))
				}
				
				Row(verticalAlignment = Alignment.CenterVertically) {
					Icon(
						imageVector = Icons.Default.LocationOn,
						contentDescription = null,
						modifier = Modifier.size(16.dp),
						tint = MaterialTheme.colorScheme.primary
					)
					Spacer(modifier = Modifier.width(4.dp))
					Text(
						text = business.direction,
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						modifier = Modifier.weight(1f),
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)
				}
				
				if (business.phone.isNotEmpty()) {
					Spacer(modifier = Modifier.height(4.dp))
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							imageVector = Icons.Default.Phone,
							contentDescription = null,
							modifier = Modifier.size(16.dp),
							tint = MaterialTheme.colorScheme.primary
						)
						Spacer(modifier = Modifier.width(4.dp))
						Text(
							text = business.phone,
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
				}
				
				todaySchedule?.let { schedule ->
					Spacer(modifier = Modifier.height(4.dp))
					Row(verticalAlignment = Alignment.CenterVertically) {
						Icon(
							imageVector = Icons.Default.AccessTime,
							contentDescription = null,
							modifier = Modifier.size(16.dp),
							tint = MaterialTheme.colorScheme.primary
						)
						Spacer(modifier = Modifier.width(4.dp))
						Text(
							text = if (schedule.isOpen && schedule.openTime != null && schedule.closeTime != null) {
								"Hoy: ${schedule.openTime} - ${schedule.closeTime}"
							} else {
								"Hoy: Cerrado"
							},
							style = MaterialTheme.typography.bodySmall,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
					}
				}
			}
		}
	}
}



fun getBusinessStatus(schedule: List<BusinessSchedule>): BusinessStatus {
	val today = normalizeDay(SimpleDateFormat("EEEE", Locale.getDefault()).format(Date()))
	Log.d("BusinessStatus", "Hoy es: $today")
	
	val todaySchedule = schedule.find { normalizeDay(it.day) == today }
	Log.d("BusinessStatus", "Horario encontrado: $todaySchedule")
	
	if (todaySchedule == null) return BusinessStatus.CLOSED
	
	if (!todaySchedule.isOpen) {
		Log.d("BusinessStatus", "Cerrado porque isOpen=false")
		return BusinessStatus.CLOSED
	}
	
	Log.d(
		"BusinessStatus",
		"Evaluando horarios: ${todaySchedule.openTime} - ${todaySchedule.closeTime}"
	)
	
	val format = SimpleDateFormat("HH:mm", Locale.getDefault())
	val now = Date()
	val openTime = format.parse(todaySchedule.openTime ?: "")
	val closeTime = format.parse(todaySchedule.closeTime ?: "")
	
	Log.d("BusinessStatus", "Ahora: ${format.format(now)} | Abre: $openTime | Cierra: $closeTime")
	
	if (openTime == null || closeTime == null) return BusinessStatus.CLOSED
	
	return when {
		now.before(openTime) -> {
			Log.d("BusinessStatus", "Cerrado (antes de abrir)")
			BusinessStatus.CLOSED
		}
		
		now.after(closeTime) -> {
			Log.d("BusinessStatus", "Cerrado (después de cerrar)")
			BusinessStatus.CLOSED
		}
		
		closeTime.time - now.time <= 30 * 60 * 1000 -> {
			Log.d("BusinessStatus", "Cierra pronto")
			BusinessStatus.CLOSING_SOON
		}
		
		else -> {
			Log.d("BusinessStatus", "Abierto")
			BusinessStatus.OPEN
		}
	}
}


fun normalizeDay(day: String): String {
	return Normalizer.normalize(day.lowercase(), Normalizer.Form.NFD)
		.replace("\\p{Mn}+".toRegex(), "") // Elimina acentos
}

enum class BusinessStatus {
	OPEN,
	CLOSING_SOON,
	CLOSED
}
