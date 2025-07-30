package com.techcode.palplato.domain.model

data class Business(
	val businessId: String = "",
	val ownerId: String = "",
	val name: String = "",
	val direction: String = "",
	val phone: String = "",
	val description: String = "",
	val date: Long = System.currentTimeMillis(),
	val logoUrl: String? = null,
	val categories: List<Category> = emptyList(),
	val schedule: List<BusinessSchedule> = emptyList() // 👈 NUEVO
)

data class Category(
	val name: String = "",
	val products: List<String> = emptyList()
)

data class BusinessSchedule(
	val day: String = "",             // Ej: "Lunes"
	val openTime: String? = null,     // Ej: "09:00"
	val closeTime: String? = null,    // Ej: "18:00"
	val isOpen: Boolean = false       // true si el negocio abre este día
)
