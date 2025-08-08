package com.techcode.palplato.data.repository.local
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_businesses")
data class FavoriteBusinessEntity(
	@PrimaryKey val businessId: String,
	val name: String,
	val logoUrl: String?,
	val description: String
)