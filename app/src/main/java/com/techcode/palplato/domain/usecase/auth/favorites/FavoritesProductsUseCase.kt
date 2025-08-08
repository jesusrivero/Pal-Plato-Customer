package com.techcode.palplato.domain.usecase.auth.favorites

import com.techcode.palplato.domain.model.room.FavoriteProduct
import com.techcode.palplato.domain.repository.FavoriteProductRepository
import javax.inject.Inject

data class FavoriteProductUseCases @Inject constructor(
	val addFavoriteProduct: AddFavoriteProductUseCase,
	val removeFavoriteProduct: RemoveFavoriteProductUseCase,
	val getFavoriteProducts: GetFavoriteProductsUseCase,
	val isFavoriteProduct: IsFavoriteProductUseCase
)

class GetFavoriteProductsUseCase @Inject constructor(
	private val repository: FavoriteProductRepository
) {
	suspend operator fun invoke() = repository.getAllFavorites()
}

class AddFavoriteProductUseCase @Inject constructor(
	private val repository: FavoriteProductRepository
) {
	suspend operator fun invoke(product: FavoriteProduct) = repository.addFavorite(product)
}

class RemoveFavoriteProductUseCase @Inject constructor(
	private val repository: FavoriteProductRepository
) {
	suspend operator fun invoke(product: FavoriteProduct) = repository.removeFavorite(product)
}

class IsFavoriteProductUseCase @Inject constructor(
	private val repository: FavoriteProductRepository
) {
	suspend operator fun invoke(productId: String) = repository.isFavorite(productId)
}
