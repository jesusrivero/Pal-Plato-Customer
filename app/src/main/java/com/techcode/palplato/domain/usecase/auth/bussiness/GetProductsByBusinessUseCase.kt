package com.techcode.palplato.domain.usecase.auth.bussiness

import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow

class GetProductsByBusinessUseCase(
	private val repository: ProductRepository
) {
	operator fun invoke(businessId: String): Flow<List<Product>> {
		return repository.getProductsByBusiness(businessId)
	}
}