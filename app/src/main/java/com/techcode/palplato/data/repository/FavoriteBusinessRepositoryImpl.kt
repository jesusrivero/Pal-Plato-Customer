package com.techcode.palplato.data.repository

import com.techcode.palplato.data.repository.local.FavoriteBusinessDao
import com.techcode.palplato.data.repository.local.FavoriteBusinessEntity
import com.techcode.palplato.domain.repository.FavoriteBusinessRepository
import kotlinx.coroutines.flow.Flow

class FavoriteBusinessRepositoryImpl(
	private val dao: FavoriteBusinessDao
) : FavoriteBusinessRepository {

	override suspend fun removeFavoriteById(businessId: String) {
		dao.deleteFavoriteById(businessId)
	}
	
	
	
	override suspend fun addFavorite(business: FavoriteBusinessEntity) {
		dao.insertFavoriteBusiness(business)
	}

	
	override fun getFavorites(): Flow<List<FavoriteBusinessEntity>> {
		return dao.getAllFavoriteBusinesses()
	}
	
	override suspend fun isFavorite(businessId: String): Boolean {
		return dao.isBusinessFavorite(businessId)
	}
	
}