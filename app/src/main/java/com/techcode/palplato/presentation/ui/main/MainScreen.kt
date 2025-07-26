package com.techcode.palplato.presentation.ui.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar


@Composable
fun MainScreen(	navController: NavController){
	
	MainScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenContent(navController: NavController) {
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text("Pa'l Plato", style = MaterialTheme.typography.titleMedium)
				},
				navigationIcon = {
					IconButton(onClick = { /* Menú o perfil */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_user),
							contentDescription = "Perfil",
							modifier = Modifier.size(24.dp)
						)
					}
				},
				actions = {
					IconButton(onClick = { /* Notificaciones */ }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(24.dp)
						)
					}
				}
			)
		},
		bottomBar = {
			BottomNavigationBar(navController)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(16.dp)
				.verticalScroll(rememberScrollState())
		) {
//			Text("Hola, Jesús", style = MaterialTheme.typography.headlineSmall)
//			Text("Pa'l Plato", style = MaterialTheme.typography.bodyMedium)
			
			Spacer(Modifier.height(16.dp))
			
			// Cuadros de estadísticas
			StatsGrid()
			
			Spacer(Modifier.height(24.dp))
			
			Text("Accesos rápidos", style = MaterialTheme.typography.titleMedium)
			QuickAccessGrid()
			
			Spacer(Modifier.height(24.dp))
			
			Text("Actividad reciente", style = MaterialTheme.typography.titleMedium)
			RecentActivityList()
		}
	}
}

@Composable
fun StatsGrid() {
	Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			StatCard(
				title = "Pedidos de hoy",
				value = "24",
				iconRes = com.techcode.palplato.R.drawable.ic_orders,
				modifier = Modifier.weight(1f)
			)
			StatCard(
				title = "Menús activos",
				value = "8",
				iconRes = com.techcode.palplato.R.drawable.ic_menu,
				modifier = Modifier.weight(1f)
			)
		}
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.spacedBy(12.dp)
		) {
			StatCard(
				title = "Ingresos del día",
				value = "$1.250",
				iconRes = com.techcode.palplato.R.drawable.ic_menu,
				modifier = Modifier.weight(1f)
			)
			StatCard(
				title = "Pedidos pendientes",
				value = "5",
				iconRes = com.techcode.palplato.R.drawable.ic_menu,
				modifier = Modifier.weight(1f)
			)
		}
	}
}


// StatCard.kt
@Composable
fun StatCard(
	title: String,
	value: String,
	iconRes: Int,
	modifier: Modifier = Modifier
) {
	Surface(
		modifier = modifier.aspectRatio(1.6f),
		shape = RoundedCornerShape(16.dp),
		tonalElevation = 1.dp,
		color = MaterialTheme.colorScheme.surface
	) {
		Column(
			modifier = Modifier.padding(12.dp),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Row(verticalAlignment = Alignment.CenterVertically) {
				Icon(
					painter = painterResource(id = iconRes),
					contentDescription = title,
					tint = MaterialTheme.colorScheme.onSurfaceVariant,
					modifier = Modifier.size(24.dp)
				)
				Spacer(Modifier.width(8.dp))
				Text(title, style = MaterialTheme.typography.labelLarge)
			}
			Text(value, style = MaterialTheme.typography.headlineSmall)
		}
	}
}




@Composable
fun QuickAccessGrid() {
	val items = listOf(
		Triple("Ver menús", Icons.Default.List, {}),
		Triple("Agregar menú", Icons.Default.Add, {}),
		Triple("Ver pedidos", Icons.Default.Receipt, {}),
		Triple("Ver Reportes", Icons.Default.BarChart, {})
	)
	
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.SpaceBetween
	) {
		items.forEach { (label, icon, onClick) ->
			Column(
				modifier = Modifier
					.weight(1f)
					.padding(horizontal = 4.dp)
					.clickable { onClick() }
					.clip(RoundedCornerShape(12.dp))
					.background(MaterialTheme.colorScheme.surfaceVariant)
					.padding(12.dp),
				horizontalAlignment = Alignment.CenterHorizontally
			) {
				Icon(icon, contentDescription = label, tint = MaterialTheme.colorScheme.primary)
				Spacer(Modifier.height(8.dp))
				Text(label, style = MaterialTheme.typography.bodySmall)
			}
		}
	}
}

@Composable
fun RecentActivityList() {
	val activities = listOf(
		Triple("Laura", "Ensalada César", "11:30"),
		Triple("Mateo", "Pescado al ajillo", "11:00"),
		Triple("Sofía", "Tacos de pollo", "10:45"),
		Triple("Carlos", "Pizza Margarita", "10:30"),
		Triple("Ana", "Sopa de verduras", "10:15"),
		
	)
	
	Column {
		activities.forEach { (name, order, time) ->
			Row(
				modifier = Modifier
					.fillMaxWidth()
					.padding(vertical = 8.dp),
				verticalAlignment = Alignment.CenterVertically
			) {
				Icon(
					imageVector = Icons.Default.Person,
					contentDescription = name,
					modifier = Modifier
						.size(36.dp)
						.background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), CircleShape)
						.padding(6.dp)
				)
				Spacer(Modifier.width(12.dp))
				Column(modifier = Modifier.weight(1f)) {
					Text(name, style = MaterialTheme.typography.bodyLarge)
					Text(order, style = MaterialTheme.typography.bodySmall)
				}
				Text(time, style = MaterialTheme.typography.bodySmall)
			}
		}
	}
}


@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
	
	val navController = rememberNavController()
	MainScreenContent(navController = navController)
}