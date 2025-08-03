package com.techcode.palplato.presentation.ui.bussines

import android.R
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.viewmodels.auth.BusinessViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import coil.request.ImageRequest

@Composable
fun BusinessDetailScreen(
	navController: NavController,
	businessId: String
) {
	BusinessDetailScreenContent(
		navController = navController,
		businessId = businessId
	)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BusinessDetailScreenContent(
	navController: NavController,
	businessId: String,
	businessViewModel: BusinessViewModel = hiltViewModel()
) {
	val business by businessViewModel.fetchBusinessById(businessId).collectAsState(initial = null)
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						"Detalles del negocio",
						maxLines = 1,
						overflow = TextOverflow.Ellipsis
					)
				},
				navigationIcon = {
					IconButton(onClick = { navController.popBackStack() }) {
						Icon(
							imageVector = Icons.Default.ArrowBack,
							contentDescription = "Volver"
						)
					}
				},
				colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
					containerColor = MaterialTheme.colorScheme.surface
				)
			)
		}
	) { innerPadding ->
		business?.let { data ->
			LazyColumn(
				modifier = Modifier
					.padding(innerPadding)
					.fillMaxSize(),
				contentPadding = PaddingValues(bottom = 16.dp),
				verticalArrangement = Arrangement.spacedBy(24.dp)
			) {
				item { BusinessDetailHeader(data) }
				item { BusinessStatusSection(data) }
				item { BusinessContactSection(data) }
				item { BusinessScheduleSection(data) }
				item { BusinessCategoriesSection(data) }
				item { BusinessActionsSection(data) }
			}
		} ?: Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(innerPadding),
			contentAlignment = Alignment.Center
		) {
			Column(
				horizontalAlignment = Alignment.CenterHorizontally,
				verticalArrangement = Arrangement.Center
			) {
				CircularProgressIndicator(
					modifier = Modifier.size(48.dp),
					strokeWidth = 4.dp
				)
				Spacer(Modifier.height(16.dp))
				Text(
					text = "Cargando información...",
					style = MaterialTheme.typography.bodyMedium,
					color = MaterialTheme.colorScheme.onSurfaceVariant
				)
			}
		}
	}
}

@Composable
fun BusinessDetailHeader(business: Business) {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.background(
				brush = Brush.verticalGradient(
					colors = listOf(
						MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
						MaterialTheme.colorScheme.surface
					)
				)
			)
	) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(24.dp),
			horizontalAlignment = Alignment.CenterHorizontally
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
						.size(120.dp)
						.clip(CircleShape)
						.background(MaterialTheme.colorScheme.surfaceVariant)
						.border(
							4.dp,
							MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
							CircleShape
						),
					placeholder = painterResource(R.drawable.gallery_thumb),
					error = painterResource(R.drawable.gallery_thumb)
				)
			} else {
				Box(
					modifier = Modifier
						.size(120.dp)
						.clip(CircleShape)
						.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
						.border(
							4.dp,
							MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),
							CircleShape
						),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = business.name.take(2).uppercase(),
						style = MaterialTheme.typography.headlineMedium,
						color = MaterialTheme.colorScheme.primary,
						fontWeight = FontWeight.Bold
					)
				}
			}
			
			Spacer(Modifier.height(16.dp))
			
			// Nombre del negocio
			Text(
				text = business.name,
				style = MaterialTheme.typography.headlineSmall,
				fontWeight = FontWeight.Bold,
				textAlign = TextAlign.Center,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis
			)
			
			// Descripción
			if (business.description.isNotEmpty()) {
				Spacer(Modifier.height(8.dp))
				Text(
					text = business.description,
					style = MaterialTheme.typography.bodyLarge,
					color = MaterialTheme.colorScheme.onSurfaceVariant,
					textAlign = TextAlign.Center,
					lineHeight = 20.sp,
					modifier = Modifier.padding(horizontal = 16.dp)
				)
			}
		}
	}
}

@Composable
fun BusinessStatusSection(business: Business) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp),
		horizontalAlignment = Alignment.CenterHorizontally
	) {
		val statusColor = if (business.isOpen) {
			MaterialTheme.colorScheme.primary
		} else {
			MaterialTheme.colorScheme.error
		}
		
		Box(
			modifier = Modifier
				.background(
					color = statusColor.copy(alpha = 0.1f),
					shape = RoundedCornerShape(16.dp)
				)
				.padding(horizontal = 20.dp, vertical = 12.dp)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically
			) {
				Canvas(modifier = Modifier.size(12.dp)) {
					drawCircle(color = statusColor)
				}
				
				Spacer(Modifier.width(8.dp))
				
				Text(
					text = if (business.isOpen) "Abierto ahora" else "Cerrado",
					style = MaterialTheme.typography.titleMedium,
					color = statusColor,
					fontWeight = FontWeight.Medium
				)
			}
		}
	}
}

@Composable
fun BusinessContactSection(business: Business) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp)
	) {
		// Título de la sección
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(bottom = 16.dp)
		) {
			Divider(
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
				thickness = 1.dp
			)
			Text(
				text = "CONTACTO",
				style = MaterialTheme.typography.labelLarge,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(horizontal = 16.dp)
			)
			Divider(
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
				thickness = 1.dp
			)
		}
		
		// Dirección
		if (business.direction.isNotEmpty()) {
			Row(
				verticalAlignment = Alignment.Top,
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp)
			) {
				Icon(
					imageVector = Icons.Default.LocationOn,
					contentDescription = "Ubicación",
					modifier = Modifier
						.size(24.dp)
						.padding(top = 2.dp),
					tint = MaterialTheme.colorScheme.primary
				)
				Spacer(Modifier.width(16.dp))
				Column(modifier = Modifier.weight(1f)) {
					Text(
						text = "Dirección",
						style = MaterialTheme.typography.labelMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						fontWeight = FontWeight.Medium
					)
					Spacer(Modifier.height(4.dp))
					Text(
						text = business.direction,
						style = MaterialTheme.typography.bodyLarge,
						lineHeight = 22.sp
					)
				}
			}
		}
		
		// Teléfono
		if (business.phone.isNotEmpty()) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp)
			) {
				Icon(
					imageVector = Icons.Default.Phone,
					contentDescription = "Teléfono",
					modifier = Modifier.size(24.dp),
					tint = MaterialTheme.colorScheme.primary
				)
				Spacer(Modifier.width(16.dp))
				Column(modifier = Modifier.weight(1f)) {
					Text(
						text = "Teléfono",
						style = MaterialTheme.typography.labelMedium,
						color = MaterialTheme.colorScheme.onSurfaceVariant,
						fontWeight = FontWeight.Medium
					)
					Spacer(Modifier.height(4.dp))
					Text(
						text = business.phone,
						style = MaterialTheme.typography.bodyLarge
					)
				}
			}
		}
	}
}

@Composable
fun BusinessScheduleSection(business: Business) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp)
	) {
		// Título de la sección
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(bottom = 16.dp)
		) {
			Divider(
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
				thickness = 1.dp
			)
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.padding(horizontal = 16.dp)
			) {
				Icon(
					imageVector = Icons.Default.Schedule,
					contentDescription = "Horarios",
					modifier = Modifier.size(18.dp),
					tint = MaterialTheme.colorScheme.primary
				)
				Spacer(Modifier.width(8.dp))
				Text(
					text = "HORARIOS",
					style = MaterialTheme.typography.labelLarge,
					fontWeight = FontWeight.Bold,
					color = MaterialTheme.colorScheme.primary
				)
			}
			Divider(
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
				thickness = 1.dp
			)
		}
		
		// Lista de horarios
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.background(
					color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
					shape = RoundedCornerShape(12.dp)
				)
				.padding(16.dp)
		) {
			business.schedule.forEachIndexed { index, schedule ->
				Row(
					modifier = Modifier
						.fillMaxWidth()
						.padding(vertical = 6.dp),
					horizontalArrangement = Arrangement.SpaceBetween,
					verticalAlignment = Alignment.CenterVertically
				) {
					Text(
						text = schedule.day,
						style = MaterialTheme.typography.bodyLarge,
						fontWeight = FontWeight.Medium,
						modifier = Modifier.weight(1f)
					)
					
					val timeText = if (schedule.openTime != null && schedule.closeTime != null) {
						"${schedule.openTime} - ${schedule.closeTime}"
					} else {
						"Cerrado"
					}
					
					Text(
						text = timeText,
						style = MaterialTheme.typography.bodyLarge,
						fontWeight = if (timeText == "Cerrado") FontWeight.Medium else FontWeight.Normal,
						color = if (timeText == "Cerrado")
							MaterialTheme.colorScheme.error
						else
							MaterialTheme.colorScheme.onSurface
					)
				}
				
				// Divider entre días (excepto el último)
				if (index < business.schedule.size - 1) {
					Divider(
						modifier = Modifier.padding(vertical = 4.dp),
						color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
						thickness = 0.5.dp
					)
				}
			}
		}
	}
}

@Composable
fun BusinessCategoriesSection(business: Business) {
	if (business.categories.isNotEmpty()) {
		Column(
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 16.dp)
		) {
			// Título de la sección
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.padding(bottom = 16.dp)
			) {
				Divider(
					modifier = Modifier.weight(1f),
					color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
					thickness = 1.dp
				)
				Row(
					verticalAlignment = Alignment.CenterVertically,
					modifier = Modifier.padding(horizontal = 16.dp)
				) {
					Icon(
						imageVector = Icons.Default.Category,
						contentDescription = "Categorías",
						modifier = Modifier.size(18.dp),
						tint = MaterialTheme.colorScheme.primary
					)
					Spacer(Modifier.width(8.dp))
					Text(
						text = "CATEGORÍAS",
						style = MaterialTheme.typography.labelLarge,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.primary
					)
				}
				Divider(
					modifier = Modifier.weight(1f),
					color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
					thickness = 1.dp
				)
			}
			
			// Chips de categorías
			FlowRow(
				horizontalArrangement = Arrangement.spacedBy(8.dp),
				verticalArrangement = Arrangement.spacedBy(8.dp)
			) {
				business.categories.forEach { category ->
					FilterChip(
						onClick = { },
						label = {
							Text(
								text = category.name,
								style = MaterialTheme.typography.labelLarge,
								fontWeight = FontWeight.Medium
							)
						},
						selected = false,
						colors = FilterChipDefaults.filterChipColors(
							containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f),
							labelColor = MaterialTheme.colorScheme.primary
						),
						border = FilterChipDefaults.filterChipBorder(
							enabled = true,
							selected = false,
							borderColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
							borderWidth = 1.dp
						)
					)
				}
			}
		}
	}
}

@Composable
fun BusinessActionsSection(business: Business) {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.padding(horizontal = 16.dp)
	) {
		// Título de la sección
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.padding(bottom = 16.dp)
		) {
			Divider(
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
				thickness = 1.dp
			)
			Text(
				text = "ACCIONES",
				style = MaterialTheme.typography.labelLarge,
				fontWeight = FontWeight.Bold,
				color = MaterialTheme.colorScheme.primary,
				modifier = Modifier.padding(horizontal = 16.dp)
			)
			Divider(
				modifier = Modifier.weight(1f),
				color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
				thickness = 1.dp
			)
		}
		
		// Botones de acción
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(16.dp)
		) {
			// Botón llamar
			if (business.phone.isNotEmpty()) {
				OutlinedButton(
					onClick = {
						// Acción para llamar
					},
					modifier = Modifier
						.weight(1f)
						.height(48.dp),
					shape = RoundedCornerShape(12.dp),
					border = BorderStroke(
						2.dp,
						MaterialTheme.colorScheme.primary
					)
				) {
					Icon(
						imageVector = Icons.Default.Phone,
						contentDescription = null,
						modifier = Modifier.size(20.dp)
					)
					Spacer(Modifier.width(8.dp))
					Text(
						text = "Llamar",
						fontWeight = FontWeight.Medium
					)
				}
			}
			
			// Botón ubicación
			if (business.direction.isNotEmpty()) {
				Button(
					onClick = {
						// Acción para ver ubicación
					},
					modifier = Modifier
						.weight(1f)
						.height(48.dp),
					shape = RoundedCornerShape(12.dp)
				) {
					Icon(
						imageVector = Icons.Default.LocationOn,
						contentDescription = null,
						modifier = Modifier.size(20.dp)
					)
					Spacer(Modifier.width(8.dp))
					Text(
						text = "Ubicación",
						fontWeight = FontWeight.Medium
					)
				}
			}
		}
	}
}
