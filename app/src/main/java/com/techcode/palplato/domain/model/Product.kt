package com.techcode.palplato.domain.model


data class Product(
	val id: String = "",
	val businessId: String = "",
	val price: Double = 0.0,  // ✅ Firestore puede convertir Long → Double automáticamente
	val name: String = "",
	val description: String = "",
	val category: String = "",
	val imageUrl: String = "",
	val preparationTime: Int = 0,
	val date: Long = System.currentTimeMillis(), // ✅ Fecha de creación por defecto
	val ingredients: List<String> = emptyList(),
	val available: Boolean = true,
	// Nuevos campos para bebidas
	val size: String? = null,
	val type: String? = null,
)
	
	

