plugins {
	alias(libs.plugins.android.application)
	alias(libs.plugins.kotlin.android)
	alias(libs.plugins.hilt.android)  // ← Usa el alias correcto
	alias(libs.plugins.jetbrainsKotlinSerialization)
	id("com.google.gms.google-services")
	id("org.jetbrains.kotlin.plugin.compose")
	id("kotlin-kapt")
}

android {
	namespace = "com.techcode.palplato"
	compileSdk = 35
	
	defaultConfig {
		applicationId = "com.techcode.palplato"
		minSdk = 24
		targetSdk = 35
		versionCode = 1
		versionName = "1.0"
		
		testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
	}
	
	buildTypes {
		release {
			isMinifyEnabled = false
			proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
		}
	}
	compileOptions {
		sourceCompatibility = JavaVersion.VERSION_11
		targetCompatibility = JavaVersion.VERSION_11
	}
	kotlinOptions {
		jvmTarget = "11"
	}
	buildFeatures {
		compose = true
	}
	
	composeOptions {
		kotlinCompilerExtensionVersion = "1.5.13"
	}
}

dependencies {
	implementation(libs.ui)
	implementation(libs.material3)
	implementation(libs.ui.tooling.preview)
	implementation(libs.androidx.navigation.compose)
	implementation(libs.kotlinx.serialization.json)
	implementation(libs.core.ktx.v1131)
	implementation(libs.androidx.material.icons.extended)
	implementation(libs.material)
	implementation(libs.datetime)
	implementation(libs.coil.compose)
	
	// Firebase - Configuración correcta
	implementation(platform(libs.firebase.bom))
	implementation(libs.firebase.auth.ktx)
	implementation(libs.firebase.firestore.ktx)
	implementation(libs.play.services.auth)
	implementation (libs.google.firebase.storage.ktx)
	implementation (libs.kotlinx.coroutines.play.services)
	
	implementation(libs.androidx.activity.compose.v190)
	implementation(libs.androidx.lifecycle.viewmodel.compose)
	
	// Hilt - Configuración corregida
	implementation(libs.hilt.android)
	kapt(libs.hilt.compiler)  // ← Solo este
	implementation(libs.androidx.hilt.navigation.compose)
	
	// DEFAULT
	implementation(libs.androidx.core.ktx)
	implementation(libs.androidx.lifecycle.runtime.ktx)
	implementation(libs.androidx.activity.compose)
	implementation(platform(libs.androidx.compose.bom))
	implementation(libs.androidx.ui)
	implementation(libs.androidx.ui.graphics)
	implementation(libs.androidx.ui.tooling.preview)
	implementation(libs.androidx.material3)
	testImplementation(libs.junit)
	androidTestImplementation(libs.androidx.junit)
	androidTestImplementation(libs.androidx.espresso.core)
	androidTestImplementation(platform(libs.androidx.compose.bom))
	androidTestImplementation(libs.androidx.ui.test.junit4)
	debugImplementation(libs.androidx.ui.tooling)
	debugImplementation(libs.androidx.ui.test.manifest)
}