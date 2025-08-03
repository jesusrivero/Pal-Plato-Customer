package com.techcode.palplato

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {
	companion object {
		lateinit var myApp: MyApplication
	}
	
	override fun onCreate() {
		super.onCreate()
		myApp = this
		
		// Inicializar ThreeTenABP
		com.jakewharton.threetenabp.AndroidThreeTen.init(this)
	}
}
