package com.techcode.palplato.domain.model

data class Business(
	val ownerId: String = "",
	val name: String = "",
	val direction: String = "",
	val phone: String = "",
	val description: String = "",
	val state: Boolean = true,
	val businessId: String = "",
	val products: List<String> = emptyList(),
	val date: Long = System.currentTimeMillis(),
	val logoUrl: String? = null,
	val isOpen: Boolean = false, // ← lo llenaremos con el estado del negocio
	val categories: List<Category> = emptyList(),
	val schedule: List<BusinessSchedule> = emptyList(),
	val id: String = "" // ← lo llenaremos con doc.id
)

data class Category(
	val name: String = "",
	val products: List<String> = emptyList()
)

data class BusinessSchedule(
	val day: String = "",
	val openTime: String? = null,
	val closeTime: String? = null,
	val isOpen: Boolean = false
)


