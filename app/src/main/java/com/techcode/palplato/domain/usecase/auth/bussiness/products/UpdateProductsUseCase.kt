package com.techcode.palplato.domain.usecase.auth.bussiness.products

import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository

class UpdateProductUseCase(
	private val productRepository: ProductRepository
) {
	suspend operator fun invoke(product: Product): Result<Unit> {
		return productRepository.updateProduct(product)
	}
}