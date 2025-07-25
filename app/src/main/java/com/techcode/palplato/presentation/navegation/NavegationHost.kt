package com.techcode.palplato.presentation.navegation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.techcode.palplato.presentation.navegation.AppRoutes.RegisterScreen
import com.techcode.palplato.presentation.ui.auth.LoginScreen
import com.techcode.palplato.presentation.ui.auth.RegisterScreen
import com.techcode.palplato.presentation.ui.order.OrderScreen
import com.techcode.palplato.presentation.ui.reports.ReporstScreen
import com.techcode.palplato.presentation.ui.main.MainScreen
import com.techcode.palplato.presentation.ui.menu.MenuScreen
import com.techcode.palplato.presentation.ui.settings.SettingsScreen

@Composable
fun NavegationHost(){
	
	val navController = rememberNavController()
	
	NavHost(navController = navController, startDestination = AppRoutes.LoginScreen) {
		
		composable<AppRoutes.MainScreen> {
			MainScreen(navController = navController)
		}
		
		composable<AppRoutes.MenuScreen> {
		MenuScreen(navController = navController)
		}
		
		composable<AppRoutes.OrderScreen> {
			OrderScreen(navController = navController)
		}
		
		composable<AppRoutes.ReporstScreen> {
			ReporstScreen(navController = navController)
		}
		
		composable<AppRoutes.SettingsScreen> {
			SettingsScreen(navController = navController)
		}
		
		composable<AppRoutes.LoginScreen> {
			LoginScreen(navController = navController)
		}
		
		composable<AppRoutes.RegisterScreen> {
			RegisterScreen(navController = navController)
		}
	}
}