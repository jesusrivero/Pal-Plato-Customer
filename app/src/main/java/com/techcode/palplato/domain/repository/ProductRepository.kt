package com.techcode.palplato.domain.repository

import android.net.Uri
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
	suspend fun createProduct(product: Product): Resource<Unit>
	suspend fun getProducts(businessId: String): Resource<List<Product>>
	suspend fun updateProduct(product: Product): Result<Unit>
	suspend fun updateAvailability(businessId: String, productId: String, available: Boolean): Result<Unit>
	suspend fun deleteProduct(businessId: String, productId: String): Result<Unit>
	suspend fun uploadProductImage(businessId: String, productId: String, imageUri: Uri): Result<String>
	fun getActiveProductsCount(businessId: String): Flow<Int>
}