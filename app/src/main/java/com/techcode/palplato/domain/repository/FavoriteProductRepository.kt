package com.techcode.palplato.domain.repository

import com.techcode.palplato.domain.model.room.FavoriteProduct

interface FavoriteProductRepository {
	suspend fun getAllFavorites(): List<FavoriteProduct>
	suspend fun addFavorite(product: FavoriteProduct)
	suspend fun removeFavorite(product: FavoriteProduct)
	suspend fun isFavorite(productId: String): Boolean
}