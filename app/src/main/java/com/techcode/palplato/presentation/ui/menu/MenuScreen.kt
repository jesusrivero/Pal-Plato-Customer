package com.techcode.palplato.presentation.ui.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.presentation.ui.commons.BottomNavigationBar

@Composable
fun MenuScreen(	navController: NavController){
	
	MenuScreenContent(navController = navController,)
	
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreenContent(navController: NavController) {
	val tabs = listOf("Todos", "Combos", "Bebidas")
	var selectedTab by rememberSaveable { mutableStateOf(0) }
	
	val items = listOf(
		MenuItem("Hamburguesa Clásica", "Carne, queso, lechuga, tomate", com.techcode.palplato.R.drawable.ic_hamburguesa),
		MenuItem("Pizza Margarita", "Salsa de tomate, mozzarella, albahaca", com.techcode.palplato.R.drawable.ic_hamburguesa),
		MenuItem("Refresco de Cola", "Bebida carbonatada",com.techcode.palplato.R.drawable.ic_hamburguesa),
		MenuItem("Pizza Margarita", "Salsa de tomate, mozzarella, albahaca", com.techcode.palplato.R.drawable.ic_hamburguesa),
		MenuItem("Refresco de Cola", "Bebida carbonatada",com.techcode.palplato.R.drawable.ic_hamburguesa),
		MenuItem("Pizza Margarita", "Salsa de tomate, mozzarella, albahaca", com.techcode.palplato.R.drawable.ic_hamburguesa),
		MenuItem("Refresco de Cola", "Bebida carbonatada",com.techcode.palplato.R.drawable.ic_hamburguesa)
	)
	
	Scaffold(
		topBar = {
			CenterAlignedTopAppBar(
				title = {
					Text("Menús", style = MaterialTheme.typography.titleMedium)
				},
				actions = {
					IconButton(onClick = { }) {
						Icon(
							painter = painterResource(id = com.techcode.palplato.R.drawable.ic_notification),
							contentDescription = "Notificaciones",
							modifier = Modifier.size(25.dp)
						)
					}
				}
			)
		},
		floatingActionButton = {
			FloatingActionButton(
				shape = CircleShape,
				onClick = { },
				containerColor = Color.Black,
				contentColor = Color.White
			) {
				Icon(Icons.Default.Add, contentDescription = "Agregar", modifier = Modifier.size(22.dp))
			}
		},
		bottomBar = {
			BottomNavigationBar(navController = navController)
		}
	) { innerPadding ->
		Column(
			modifier = Modifier
				.padding(innerPadding)
				.padding(horizontal = 16.dp)
		) {
			ScrollableTabRow(
				selectedTabIndex = selectedTab,
				edgePadding = 0.dp,
				indicator = {},
				divider = {}
			) {
				tabs.forEachIndexed { index, title ->
					Tab(
						selected = selectedTab == index,
						onClick = { selectedTab = index },
						text = {
							Text(
								text = title,
								fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
								color = if (selectedTab == index) Color.Black else Color.Gray
							)
						}
					)
				}
			}
			
			Spacer(modifier = Modifier.height(16.dp))
			
			LazyVerticalGrid(
				columns = GridCells.Fixed(2),
				verticalArrangement = Arrangement.spacedBy(16.dp),
				horizontalArrangement = Arrangement.spacedBy(16.dp),
				modifier = Modifier.fillMaxSize()
			) {
				items(items) { item ->
					MenuCard(item)
				}
			}
		}
	}
}

@Composable
fun MenuCard(item: MenuItem) {
	Column(
		modifier = Modifier
			.clip(RoundedCornerShape(12.dp))
	) {
		Image(
			painter = painterResource(id = item.imageRes),
			contentDescription = item.title,
			modifier = Modifier
				.height(120.dp)
				.fillMaxWidth()
				.clip(RoundedCornerShape(12.dp)),
			contentScale = ContentScale.Crop
		)
		
		Spacer(modifier = Modifier.height(8.dp))
		
		Text(
			text = item.title,
			fontWeight = FontWeight.SemiBold,
			style = MaterialTheme.typography.bodyMedium
		)
		
		Text(
			text = item.description,
			color = Color.Gray,
			style = MaterialTheme.typography.bodySmall
		)
	}
}

data class MenuItem(
	val title: String,
	val description: String,
	val imageRes: Int
)


//@Preview(showBackground = true)
//@Composable
//fun MenuScreenPreview() {
//
//	val navController = rememberNavController()
//	MenuScreenContent(navController = navController)
//}