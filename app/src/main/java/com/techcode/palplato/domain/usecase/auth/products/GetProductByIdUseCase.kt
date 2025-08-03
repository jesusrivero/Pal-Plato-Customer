package com.techcode.palplato.domain.usecase.auth.products

import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
	private val repository: ProductRepository
) {
	operator fun invoke(businessId: String, productId: String): Flow<Product?> {
		return repository.getProductById(businessId, productId)
	}
}