package com.techcode.palplato.domain.usecase.auth.bussiness.products

import com.techcode.palplato.domain.repository.ProductRepository


class UpdateProductAvailabilityUseCase(
	private val productRepository: ProductRepository
) {
	suspend operator fun invoke(businessId: String, productId: String, available: Boolean): Result<Unit> {
		return productRepository.updateAvailability(businessId, productId, available)
	}
}
