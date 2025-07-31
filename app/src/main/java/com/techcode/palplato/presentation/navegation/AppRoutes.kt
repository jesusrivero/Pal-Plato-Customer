package com.techcode.palplato.presentation.navegation


import kotlinx.serialization.Serializable

object AppRoutes {
	
	@Serializable
	data object MainScreen
	
	@Serializable
	data object OrderScreen
	
	@Serializable
	data object MenuScreen
	
	@Serializable
	data class EditedMenuRoute(val productId: String) // <


//	@Serializable
//	data object MenuScreen
//
//	@Serializable
//	data object EditedMenuScreen
	
	@Serializable
	data object CreateBussinessScreen
	
//	@Serializable
//	data object TutorialScreen
	
	@Serializable
	data object ReporstScreen
	
	@Serializable
	data object SettingsScreen
	
	@Serializable
	data object LoginScreen
	
	@Serializable
	data object RecoverPasswordScreen
	
	@Serializable
	data object RegisterScreen
	
	@Serializable
	data object SplashScreen
	
	@Serializable
	data object OrderDetailsScreen
	
	@Serializable
	data object CreateMenuScreen
	
	@Serializable
	data object EditedProfileScreen
	
//	Edited Name
	@Serializable
	data object EditedNameScreen
	
	@Serializable
	data object EditedEmailScreen
	
	@Serializable
	data object EditedPhoneScreen
	
	//	Edited Business
	@Serializable
	data object EditedBussinessScreen
	
	@Serializable
	data object EditedDatesBussinessScreen
	
	@Serializable
	data object EditedschedulesBusseinessScreen
	//	Edited Security
	@Serializable
	data object EditedSecurityScreen
	
	@Serializable
	data object EditedPasswordScreen
	
	//	Edited Security
	@Serializable
	data object EditedNotificationPreferencesScreen
	
	
	
//
//	@Serializable
//	data object LoginScreen
//
//	@Serializable
//	data object RegisterScreen
//
//	@Serializable
//	data object CreateBusinessScreen
}
