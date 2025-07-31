package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.tasks.await

class ProductRepositoryImpl(
	private val firestore: FirebaseFirestore
) : ProductRepository {
	
	override suspend fun createProduct(product: Product): Resource<Unit> {
		return try {
			val productId = firestore.collection("businesses")
				.document(product.businessId)
				.collection("products")
				.document().id
			
			// ✅ Agregamos el ID y la fecha de creación
			val productWithId = product.copy(
				id = productId,
				date = System.currentTimeMillis()
			)
			
			firestore.collection("businesses")
				.document(product.businessId)
				.collection("products")
				.document(productId)
				.set(productWithId)
				.await()
			
			Resource.Success(Unit)
		} catch (e: Exception) {
			Resource.Error(e.message ?: "Error al crear el producto")
		}
	}
	
	
	
	override suspend fun getProducts(businessId: String): Resource<List<Product>> {
		return try {
			val snapshot = firestore.collection("businesses")
				.document(businessId)
				.collection("products")
				.orderBy("date", Query.Direction.DESCENDING)
				.get()
				.await()
			
			val products = snapshot.documents.mapNotNull { it.toObject(Product::class.java) }
			Resource.Success(products)
		} catch (e: Exception) {
			Resource.Error(e.message ?: "Error al obtener los productos")
		}
	}
	
	override suspend fun updateProduct(product: Product): Result<Unit> {
		return try {
			val updates = mapOf(
				"name" to product.name,
				"description" to product.description,
				"price" to product.price,
				"category" to product.category,
				"imageUrl" to product.imageUrl,
				"preparationTime" to product.preparationTime,
				"ingredients" to product.ingredients,
				"available" to product.available
			)
			
			firestore.collection("businesses") // ← corregido aquí
				.document(product.businessId)
				.collection("products")
				.document(product.id)
				.update(updates)
				.await()
			
			Result.success(Unit)
		} catch (e: Exception) {
			Result.failure(e)
		}
	}

}