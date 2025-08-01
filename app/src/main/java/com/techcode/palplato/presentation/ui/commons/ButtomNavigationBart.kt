package com.techcode.palplato.presentation.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.techcode.palplato.domain.helpers.navRoute
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.techcode.palplato.presentation.navegation.AppRoutes


//@Composable
//fun BottomNavigationBar(
//	navController: NavController,
//	modifier: Modifier = Modifier,
//) {
//	val navBackStackEntry by navController.currentBackStackEntryAsState()
//	val currentRoute = navBackStackEntry?.destination?.route
//
//	val mainRoute = navRoute<AppRoutes.MainScreen>()
//	val manuRoute = navRoute<AppRoutes.MenuScreen>()
//	val ordersRoute = navRoute<AppRoutes.OrderScreen>()
//	val reportsRoute = navRoute<AppRoutes.ReporstScreen>()
//	val settingsRoute = navRoute<AppRoutes.SettingsScreen>()
//
//	NavigationBar(
//		containerColor = MaterialTheme.colorScheme.surfaceVariant,
//		contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
//		modifier = modifier
//	) {
//		NavigationBarItem(
//			icon = {
//				Icon(
//					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_home),
//					contentDescription = "Inicio", modifier = Modifier.size(20.dp)
//				)
//			},
//			label = { Text("Home") },
//			selected = currentRoute == mainRoute,
//			onClick = {
//				navigateIfNeeded(navController, mainRoute, currentRoute)
//			},
//			colors = NavigationBarItemDefaults.colors(
//				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				indicatorColor = Color.Transparent
//			)
//		)
//
//		NavigationBarItem(
//			icon = {
//				Icon(
//					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_menu),
//					contentDescription = "Menus", modifier = Modifier.size(20.dp)
//				)
//			},
//			label = { Text("Menus") },
//			selected = currentRoute == manuRoute,
//			onClick = {
//				navigateIfNeeded(navController, manuRoute, currentRoute)
//			},
//			colors = NavigationBarItemDefaults.colors(
//				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				indicatorColor = Color.Transparent
//			)
//		)
//
//		NavigationBarItem(
//			icon = {
//				Icon(
//					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_orders),
//					contentDescription = "Ordenes", modifier = Modifier.size(20.dp)
//				)
//			},
//			label = { Text("Ordenes") },
//			selected = currentRoute == ordersRoute,
//			onClick = {
//				navigateIfNeeded(navController, ordersRoute, currentRoute)
//			},
//			colors = NavigationBarItemDefaults.colors(
//				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				indicatorColor = Color.Transparent
//			)
//		)
//
//		NavigationBarItem(
//			icon = {
//				Icon(
//					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_reports),
//					contentDescription = "Reportes", modifier = Modifier.size(20.dp)
//				)
//			},
//			label = { Text("Reportes") },
//			selected = currentRoute == reportsRoute,
//			onClick = {
//				navigateIfNeeded(navController, reportsRoute, currentRoute)
//			},
//			colors = NavigationBarItemDefaults.colors(
//				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				indicatorColor = Color.Transparent
//			)
//		)
//
//		NavigationBarItem(
//			icon = {
//				Icon(
//					painter = painterResource(id = com.techcode.palplato.R.drawable.ic_settings),
//					contentDescription = "Ajustes", modifier = Modifier.size(20.dp)
//				)
//			},
//			label = { Text("Ajustes") },
//			selected = currentRoute == settingsRoute,
//			onClick = {
//				navigateIfNeeded(navController, settingsRoute, currentRoute)
//			},
//			colors = NavigationBarItemDefaults.colors(
//				selectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				selectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 1f),
//				unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
//				indicatorColor = Color.Transparent
//			)
//		)
//	}
//}

@Composable
fun BottomNavigationBar(
	navController: NavController,
	modifier: Modifier = Modifier
) {
	val navBackStackEntry by navController.currentBackStackEntryAsState()
	val currentRoute = navBackStackEntry?.destination?.route
	
	val mainRoute = navRoute<AppRoutes.MainScreen>()
	val menuRoute = navRoute<AppRoutes.MenuScreen>()
	val ordersRoute = navRoute<AppRoutes.OrderScreen>()
	val reportsRoute = navRoute<AppRoutes.ReporstScreen>()
	val settingsRoute = navRoute<AppRoutes.SettingsScreen>()
	val customBlue = Color(0xFFD0A424) // Color personalizado
	
	Surface(
		tonalElevation = 6.dp,
		shadowElevation = 8.dp,
		shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
		modifier = modifier
	) {
		NavigationBar(
			containerColor = customBlue, // Cambiar color de fondo
			contentColor = Color.White,
			tonalElevation = 0.dp
		) {
			val items = listOf(
				Triple("Home", com.techcode.palplato.R.drawable.ic_home, mainRoute),
				Triple("Menus", com.techcode.palplato.R.drawable.ic_menu, menuRoute),
				Triple("Órdenes", com.techcode.palplato.R.drawable.ic_orders, ordersRoute),
				Triple("Reportes", com.techcode.palplato.R.drawable.ic_reports, reportsRoute),
				Triple("Ajustes", com.techcode.palplato.R.drawable.ic_settings, settingsRoute)
			)
			
			items.forEach { (label, icon, route) ->
				val selected = currentRoute == route
				NavigationBarItem(
					icon = {
						Box(contentAlignment = Alignment.Center) {
							if (selected) {
								Box(
									modifier = Modifier
										.size(36.dp)
										.background(
											color = Color.White.copy(alpha = 0.15f),
											shape = CircleShape
										)
								)
							}
							Icon(
								painter = painterResource(id = icon),
								contentDescription = label,
								modifier = Modifier.size(22.dp),
								tint = if (selected) Color.White else Color.White.copy(alpha = 0.7f)
							)
						}
					},
					label = {
						if (selected) {
							Text(
								label,
								style = MaterialTheme.typography.labelMedium,
								color = Color.White
							)
						}
					},
					selected = selected,
					onClick = { navigateIfNeeded(navController, route, currentRoute) },
					alwaysShowLabel = false,
					colors = NavigationBarItemDefaults.colors(
						selectedIconColor = Color.White,
						selectedTextColor = Color.White,
						unselectedIconColor = Color.White.copy(alpha = 0.7f),
						unselectedTextColor = Color.White.copy(alpha = 0.6f),
						indicatorColor = Color.Transparent
					)
				)
			}
		}
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