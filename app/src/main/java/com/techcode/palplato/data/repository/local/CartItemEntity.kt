package com.techcode.palplato.data.repository.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "cart_items")
data class CartItemEntity(
	@PrimaryKey val productId: String,
	val productName: String,
	val price: Double,
	val quantity: Int,
	val businessId: String,
	val imageUrl: String // ← agregado
)

