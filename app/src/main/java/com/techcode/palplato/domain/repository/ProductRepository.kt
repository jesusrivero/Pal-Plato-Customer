package com.techcode.palplato.domain.repository

import com.techcode.palplato.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
	fun getProductsByBusiness(businessId: String): Flow<List<Product>>
	fun getProductById(businessId: String, productId: String): Flow<Product?>
}