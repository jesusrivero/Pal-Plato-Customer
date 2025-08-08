package com.techcode.palplato.domain.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_products")
data class FavoriteProduct(
	@PrimaryKey val id: String,
	val businessId: String,
	val price: Double,
	val name: String,
	val description: String,
	val category: String,
	val imageUrl: String,
	val preparationTime: Int,
	val date: Long,
	val available: Boolean,
	val size: String?,
	val type: String?
)