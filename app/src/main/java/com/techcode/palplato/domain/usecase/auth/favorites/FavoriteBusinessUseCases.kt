package com.techcode.palplato.domain.usecase.auth.favorites

import com.techcode.palplato.data.repository.local.FavoriteBusinessEntity
import com.techcode.palplato.domain.repository.FavoriteBusinessRepository
import kotlinx.coroutines.flow.Flow

data class FavoriteBusinessUseCases(
	val addFavorite: AddFavorite,
	val removeFavorite: RemoveFavorite,
	val getFavorites: GetFavorites,
	val isFavorite: IsFavorite
)
class AddFavorite(private val repository: FavoriteBusinessRepository) {
	suspend operator fun invoke(entity: FavoriteBusinessEntity) = repository.addFavorite(entity)
}

class RemoveFavorite(private val repository: FavoriteBusinessRepository) {
	suspend operator fun invoke(businessId: String) = repository.removeFavoriteById(businessId)
}

class GetFavorites(private val repository: FavoriteBusinessRepository) {
	operator fun invoke(): Flow<List<FavoriteBusinessEntity>> = repository.getFavorites()
}

class IsFavorite(private val repository: FavoriteBusinessRepository) {
	suspend operator fun invoke(businessId: String): Boolean = repository.isFavorite(businessId)
}

