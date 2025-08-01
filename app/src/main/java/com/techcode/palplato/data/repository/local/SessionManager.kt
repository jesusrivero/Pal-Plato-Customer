package com.techcode.palplato.data.repository.local

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(
	@ApplicationContext context: Context
) {
	private val prefs: SharedPreferences =
		context.getSharedPreferences("session_prefs", Context.MODE_PRIVATE)
	
	companion object {
		private const val KEY_IS_LOGGED_IN = "is_logged_in"
		private const val KEY_UID = "uid"
		private const val KEY_BUSINESS_ID = "business_id"
	}
	
	fun saveSession(uid: String) {
		prefs.edit()
			.putBoolean(KEY_IS_LOGGED_IN, true)
			.putString(KEY_UID, uid)
			.apply()
	}
	
	fun isLoggedIn(): Boolean {
		return prefs.getBoolean(KEY_IS_LOGGED_IN, false)
	}
	
	fun clearSession() {
		prefs.edit()
			.putBoolean(KEY_IS_LOGGED_IN, false)
			.remove(KEY_UID)
			.remove(KEY_BUSINESS_ID)
			.apply()
	}
	
	/** ✅ UID del usuario actual */
	fun getUserUid(): String? = prefs.getString(KEY_UID, null)
	
	fun saveBusinessId(businessId: String) {
		prefs.edit()
			.putString(KEY_BUSINESS_ID, businessId)
			.apply()
	}
	
	fun getBusinessId(): String? {
		return prefs.getString(KEY_BUSINESS_ID, null)
	}
}



