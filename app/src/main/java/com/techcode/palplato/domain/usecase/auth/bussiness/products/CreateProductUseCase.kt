package com.techcode.palplato.domain.usecase.auth.bussiness.products

import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository
import com.techcode.palplato.utils.Resource

class CreateProductUseCase(
	private val repository: ProductRepository
) {
	suspend operator fun invoke(product: Product): Resource<Unit> {
		return repository.createProduct(product)
	}
}