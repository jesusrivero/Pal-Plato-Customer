package com.techcode.palplato.domain.model

data class CartItem(
	val productId: String,
	val productName: String,
	val price: Double,
	val quantity: Int,
	val businessId: String,
	val imageUrl: String // ← agregado
)
