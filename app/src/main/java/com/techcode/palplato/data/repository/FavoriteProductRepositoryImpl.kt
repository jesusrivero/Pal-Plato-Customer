package com.techcode.palplato.data.repository

import com.techcode.palplato.data.repository.local.FavoriteProductDao
import com.techcode.palplato.domain.model.room.FavoriteProduct
import com.techcode.palplato.domain.repository.FavoriteProductRepository

class FavoriteProductRepositoryImpl(
	private val dao: FavoriteProductDao
) : FavoriteProductRepository {
	
	override suspend fun getAllFavorites(): List<FavoriteProduct> = dao.getAllFavorites()
	
	override suspend fun addFavorite(product: FavoriteProduct) = dao.addFavorite(product)
	
	override suspend fun removeFavorite(product: FavoriteProduct) = dao.removeFavorite(product)
	
	override suspend fun isFavorite(productId: String): Boolean = dao.isFavorite(productId)
}