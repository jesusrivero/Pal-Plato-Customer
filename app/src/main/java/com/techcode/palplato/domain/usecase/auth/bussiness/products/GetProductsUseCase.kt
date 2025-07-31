package com.techcode.palplato.domain.usecase.auth.bussiness.products

import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository
import com.techcode.palplato.utils.Resource

class GetProductsUseCase(
	private val repository: ProductRepository
) {
	suspend operator fun invoke(businessId: String): Resource<List<Product>> {
		return repository.getProducts(businessId)
	}
}