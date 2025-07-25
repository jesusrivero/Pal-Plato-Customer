package com.techcode.palplato.presentation.ui.commons

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.techcode.palplato.domain.helpers.navRoute
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.techcode.palplato.presentation.navegation.AppRoutes


@Composable
fun BottomNavigationBar(
	navController: NavController,
	modifier: Modifier = Modifier,
) {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route
	
	val mainRoute = navRoute<AppRoutes.MainScreen>()
	val manuRoute = navRoute<AppRoutes.MenuScreen>()
	val ordersRoute = navRoute<AppRoutes.OrderScreen>()
	val reportsRoute = navRoute<AppRoutes.ReporstScreen>()
	val settingsRoute = navRoute<AppRoutes.SettingsScreen>()
	
	NavigationBar(
		containerColor = MaterialTheme.colorScheme.surfaceVariant,
		contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
		modifier = modifier
	) {
		NavigationBarItem(
			icon = {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_home),
					contentDescription = "Inicio", modifier = Modifier.size(20.dp)
				)
			},
			label = { Text("Home") },
			selected = currentRoute == mainRoute,
			onClick = {
				navigateIfNeeded(navController, mainRoute, currentRoute)
			},
			colors = NavigationBarItemDefaults.colors(
				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				indicatorColor = Color.Transparent
			)
		)
		
		NavigationBarItem(
			icon = {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_menu),
					contentDescription = "Menus", modifier = Modifier.size(20.dp)
				)
			},
			label = { Text("Menus") },
			selected = currentRoute == manuRoute,
			onClick = {
				navigateIfNeeded(navController, manuRoute, currentRoute)
			},
			colors = NavigationBarItemDefaults.colors(
				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				indicatorColor = Color.Transparent
			)
		)
		
		NavigationBarItem(
			icon = {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_orders),
					contentDescription = "Ordenes", modifier = Modifier.size(20.dp)
				)
			},
			label = { Text("Ordenes") },
			selected = currentRoute == ordersRoute,
			onClick = {
				navigateIfNeeded(navController, ordersRoute, currentRoute)
			},
			colors = NavigationBarItemDefaults.colors(
				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				indicatorColor = Color.Transparent
			)
		)
		
		NavigationBarItem(
			icon = {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_reports),
					contentDescription = "Reportes", modifier = Modifier.size(20.dp)
				)
			},
			label = { Text("Reportes") },
			selected = currentRoute == reportsRoute,
			onClick = {
				navigateIfNeeded(navController, reportsRoute, currentRoute)
			},
			colors = NavigationBarItemDefaults.colors(
				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				indicatorColor = Color.Transparent
			)
		)
		
		NavigationBarItem(
			icon = {
				Icon(
					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_settings),
					contentDescription = "Ajustes", modifier = Modifier.size(20.dp)
				)
			},
			label = { Text("Ajustes") },
			selected = currentRoute == settingsRoute,
			onClick = {
				navigateIfNeeded(navController, settingsRoute, currentRoute)
			},
			colors = NavigationBarItemDefaults.colors(
				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
				indicatorColor = Color.Transparent
			)
		)
	}
}


fun navigateIfNeeded(
	navController: NavController,
	destination: String,
	currentRoute: String?
) {
	if (currentRoute != destination) {
		navController.navigate(destination) {
			launchSingleTop = true
			restoreState = true
			popUpTo(navController.graph.startDestinationId) {
				saveState = true
			}
		}
	}
}