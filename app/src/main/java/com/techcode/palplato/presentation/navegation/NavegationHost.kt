package com.techcode.palplato.presentation.navegation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

import com.techcode.palplato.presentation.navegation.AppRoutes.FavoriteScreen
import com.techcode.palplato.presentation.ui.auth.LoginScreen
import com.techcode.palplato.presentation.ui.auth.RecoverPasswordScreen
import com.techcode.palplato.presentation.ui.auth.RegisterScreen
import com.techcode.palplato.presentation.ui.auth.SplashScreen
import com.techcode.palplato.presentation.ui.bussines.AllBusinessesScreen
import com.techcode.palplato.presentation.ui.bussines.BusinessDetailScreen
import com.techcode.palplato.presentation.ui.bussines.GetAllProductsBusinessScreen
import com.techcode.palplato.presentation.ui.order.OrderScreen
import com.techcode.palplato.presentation.ui.reports.ReporstScreen
import com.techcode.palplato.presentation.ui.main.MainScreen
import com.techcode.palplato.presentation.ui.favorites.FavoriteScreen
import com.techcode.palplato.presentation.ui.order.OrderDetailsScreen
import com.techcode.palplato.presentation.ui.products.ProductsDetailScreenContent
import com.techcode.palplato.presentation.ui.settings.EditedEmailScreen
import com.techcode.palplato.presentation.ui.settings.EditedNameScreen
import com.techcode.palplato.presentation.ui.settings.EditedNotificationPreferencesScreen
import com.techcode.palplato.presentation.ui.settings.EditedPasswordScreen
import com.techcode.palplato.presentation.ui.settings.EditedPhoneScreen
import com.techcode.palplato.presentation.ui.settings.EditedProfileScreen
import com.techcode.palplato.presentation.ui.settings.EditedSecurityScreen
import com.techcode.palplato.presentation.ui.settings.SettingsScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavegationHost(){
	
	val navController = rememberNavController()
	
	NavHost(navController = navController, startDestination = AppRoutes.SplashScreen) {
		
		composable<AppRoutes.MainScreen> {
			MainScreen(navController = navController)
		}
		
		composable<AppRoutes.SplashScreen> {
			SplashScreen(navController = navController)
		}
		
		composable<FavoriteScreen> {
			FavoriteScreen(navController = navController)
		}
		
		
//
//		composable<AppRoutes.MenuScreen> {
//		MenuScreen(navController = navController)
//		}
//
//		composable<AppRoutes.EditedMenuScreen> {
//			EditedMenuScreen(navController = navController)
//		}
		
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
		
		composable<AppRoutes.RecoverPasswordScreen> {
			RecoverPasswordScreen(navController = navController)
		}
		
		composable<AppRoutes.RegisterScreen> {
			RegisterScreen(navController = navController)
		}
		
		
		composable<AppRoutes.OrderDetailsScreen> {
			OrderDetailsScreen(navController = navController)
		}
		
		
	
		
		composable<AppRoutes.EditedProfileScreen> {
			EditedProfileScreen(navController = navController)
		}
		
		composable<AppRoutes.EditedNameScreen> {
			EditedNameScreen(navController = navController)
		}
		
		composable<AppRoutes.EditedEmailScreen> {
			EditedEmailScreen(navController = navController)
		}
		composable<AppRoutes.EditedPhoneScreen> {
			EditedPhoneScreen(navController = navController)
		}
		
		
		composable<AppRoutes.EditedSecurityScreen> {
			EditedSecurityScreen(navController = navController)
		}
		
		composable<AppRoutes.EditedPasswordScreen> {
			EditedPasswordScreen(navController = navController)
		}
		
		composable<AppRoutes.EditedNotificationPreferencesScreen> {
			EditedNotificationPreferencesScreen(navController = navController)
		}
		
		composable<AppRoutes.AllBusinessesScreen> {
			AllBusinessesScreen(navController = navController)
		}
		
		composable<AppRoutes.GetAllProductsBusinessScreen> { backStackEntry ->
			val args = backStackEntry.toRoute<AppRoutes.GetAllProductsBusinessScreen>()
			GetAllProductsBusinessScreen(
				navController = navController,
				businessId = args.businessId
			)
		}
		
		composable<AppRoutes.BusinessDetailScreen> { backStackEntry ->
			val args = backStackEntry.toRoute<AppRoutes.BusinessDetailScreen>()
			BusinessDetailScreen(navController, args.businessId)
		}
		
		
		
		composable<AppRoutes.ProductDetailScreen> { backStackEntry ->
			val args = backStackEntry.toRoute<AppRoutes.ProductDetailScreen>()
			ProductsDetailScreenContent(
				navController = navController,
				businessId = args.businessId,
				productId = args.productId
			)
		}
//
//		composable<AppRoutes.GetAllProductsBusinessScreen> {
//			GetAllProductsBusinessScreen(navController = navController)
//		}

	}
}