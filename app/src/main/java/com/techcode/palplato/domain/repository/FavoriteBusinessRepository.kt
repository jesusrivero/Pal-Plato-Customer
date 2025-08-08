package com.techcode.palplato.domain.repository

import com.techcode.palplato.data.repository.local.FavoriteBusinessEntity
import kotlinx.coroutines.flow.Flow

interface FavoriteBusinessRepository {
	suspend fun addFavorite(business: FavoriteBusinessEntity)
	suspend fun removeFavoriteById(businessId: String)
	fun getFavorites(): Flow<List<FavoriteBusinessEntity>>
	suspend fun isFavorite(businessId: String): Boolean
	
}