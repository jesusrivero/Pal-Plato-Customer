package com.techcode.palplato.presentation.ui.reports

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.R
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar
import java.text.NumberFormat
import java.util.*

@Composable
fun ReporstScreen(navController: NavController) {
	
	ReporstScreenContent(navController = navController)
	
}


// Modelos de datos
data class MenuItem(
	val id: String,
	val name: String,
	val ordersCount: Int,
	val category: String,
)

data class DayReport(
	val totalSales: Double,
	val completedOrders: Int,
	val topMenuItems: List<MenuItem>,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReporstScreenContent(navController: NavController) {
	// Datos de ejemplo - en tu app estos vendrían de tu base de datos/API
	val sampleReport = DayReport(
		totalSales = 1250000.0, // $1,250,000 COP
		completedOrders = 45,
		topMenuItems = listOf(
			MenuItem("1", "Hamburguesa Clásica", 12, "Hamburguesas"),
			MenuItem("2", "Pizza Pepperoni", 8, "Pizzas"),
			MenuItem("3", "Pollo Broaster", 7, "Pollo"),
			MenuItem("4", "Perro Caliente", 6, "Hot Dogs"),
			MenuItem("5", "Papas Fritas", 5, "Acompañantes")
		)
	)
	
	val currencyFormat = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text(
						text = "Reportes",
						style = MaterialTheme.typography.titleMedium
					)
				},
				
				actions = {
					
					IconButton(onClick = { /* Acción de notificaciones */ }) {
						Icon(
							painter = painterResource(id = R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				onClick = {  },
				containerColor = MaterialTheme.colorScheme.primary,
				contentColor = MaterialTheme.colorScheme.onPrimary
			) {
				Icon(
					imageVector = Icons.Default.Assessment,
					contentDescription = "Generar reporte"
				)
			}
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		}
	) { innerPadding ->
		LazyColumn(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 20.dp),
			verticalArrangement = Arrangement.spacedBy(32.dp)
		) {
			item { Spacer(modifier = Modifier.height(8.dp)) }
			
			// Métricas principales
			item {
				Row(
					modifier = Modifier.fillMaxWidth(),
					horizontalArrangement = Arrangement.spacedBy(20.dp)
				) {
					// Total de Ventas
					Column(
						modifier = Modifier.weight(1f),
						horizontalAlignment = Alignment.Start
					) {
						Text(
							text = "Ventas del día",
							style = MaterialTheme.typography.bodyMedium,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
						Spacer(modifier = Modifier.height(4.dp))
						Text(
							text = currencyFormat.format(sampleReport.totalSales),
							style = MaterialTheme.typography.headlineMedium,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.onSurface
						)
					}
					
					// Pedidos Completados
					Column(
						modifier = Modifier.weight(1f),
						horizontalAlignment = Alignment.Start
					) {
						Text(
							text = "Pedidos completados",
							style = MaterialTheme.typography.bodyMedium,
							color = MaterialTheme.colorScheme.onSurfaceVariant
						)
						Spacer(modifier = Modifier.height(4.dp))
						Text(
							text = sampleReport.completedOrders.toString(),
							style = MaterialTheme.typography.headlineMedium,
							fontWeight = FontWeight.Bold,
							color = MaterialTheme.colorScheme.onSurface
						)
					}
				}
			}
			
			// Divider sutil
			item {
				HorizontalDivider(
					modifier = Modifier.padding(vertical = 8.dp),
					color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
				)
			}
			
			// Gráfico de Menús Más Vendidos
			item {
				Column {
					Text(
						text = "Productos más vendidos",
						style = MaterialTheme.typography.titleMedium,
						fontWeight = FontWeight.SemiBold,
						color = MaterialTheme.colorScheme.onSurface,
						modifier = Modifier.padding(bottom = 20.dp)
					)
					
					Column(
						verticalArrangement = Arrangement.spacedBy(16.dp)
					) {
						sampleReport.topMenuItems.forEachIndexed { index, item ->
							MenuItemRow(
								item = item,
								maxCount = sampleReport.topMenuItems.firstOrNull()?.ordersCount ?: 1,
								position = index + 1
							)
						}
					}
				}
			}
			
			item { Spacer(modifier = Modifier.height(16.dp)) }
		}
	}
}

@Composable
fun MenuItemRow(
	item: MenuItem,
	maxCount: Int,
	position: Int,
) {
	val percentage = (item.ordersCount.toFloat() / maxCount.toFloat())
	
	Column {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				modifier = Modifier.weight(1f)
			) {
				// Número de posición
				Box(
					modifier = Modifier
						.size(24.dp)
						.background(
							MaterialTheme.colorScheme.primaryContainer,
							RoundedCornerShape(6.dp)
						),
					contentAlignment = Alignment.Center
				) {
					Text(
						text = position.toString(),
						style = MaterialTheme.typography.labelSmall,
						fontWeight = FontWeight.Bold,
						color = MaterialTheme.colorScheme.onPrimaryContainer
					)
				}
				
				Spacer(modifier = Modifier.width(12.dp))
				
				Column {
					Text(
						text = item.name,
						style = MaterialTheme.typography.bodyMedium,
						fontWeight = FontWeight.Medium,
						color = MaterialTheme.colorScheme.onSurface
					)
					Text(
						text = item.category,
						style = MaterialTheme.typography.bodySmall,
						color = MaterialTheme.colorScheme.onSurfaceVariant
					)
				}
			}
			
			Text(
				text = "${item.ordersCount}",
				style = MaterialTheme.typography.titleSmall,
				fontWeight = FontWeight.SemiBold,
				color = MaterialTheme.colorScheme.primary
			)
		}
		
		Spacer(modifier = Modifier.height(8.dp))
		
		// Barra de progreso minimalista
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.height(4.dp)
		) {
			// Fondo de la barra
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background(
						MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
						RoundedCornerShape(2.dp)
					)
			)
			// Barra de progreso
			Box(
				modifier = Modifier
					.fillMaxWidth(percentage)
					.fillMaxHeight()
					.background(
						MaterialTheme.colorScheme.primary,
						RoundedCornerShape(2.dp)
					)
			)
		}
	}
}

@Preview(showBackground = true)
@Composable
fun ReporstScreenPreview() {
	
	val navController = rememberNavController()
	ReporstScreenContent(navController = navController)
}

