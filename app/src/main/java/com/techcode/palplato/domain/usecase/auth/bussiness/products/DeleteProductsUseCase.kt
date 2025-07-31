package com.techcode.palplato.domain.usecase.auth.bussiness.products

import com.techcode.palplato.domain.repository.ProductRepository

class DeleteProductUseCase(
	private val productRepository: ProductRepository
) {
	suspend operator fun invoke(businessId: String, productId: String): Result<Unit> {
		return productRepository.deleteProduct(businessId, productId)
	}
}