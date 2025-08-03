package com.techcode.palplato.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.domain.model.Product
import com.techcode.palplato.domain.repository.ProductRepository
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProductRepositoryImpl(
	private val firestore: FirebaseFirestore,
	private val storage: FirebaseStorage
) : ProductRepository {
	
	override fun getProductsByBusiness(businessId: String): Flow<List<Product>> = callbackFlow {
		val listener = firestore.collection("businesses")
			.document(businessId)
			.collection("products")
			.whereEqualTo("available", true)
			.addSnapshotListener { snapshot, error ->
				if (error != null) {
					close(error)
					return@addSnapshotListener
				}
				
				val products = snapshot?.documents?.mapNotNull { doc ->
					doc.toObject(Product::class.java)?.copy(
						id = doc.id,
						businessId = businessId // 👈 aseguramos que se asigne correctamente
					)
				} ?: emptyList()
				
				trySend(products)
			}
		
		awaitClose { listener.remove() }
	}
	
	override fun getProductById(businessId: String, productId: String): Flow<Product?> = callbackFlow {
		val listener = firestore.collection("businesses")
			.document(businessId)
			.collection("products")
			.document(productId)
			.addSnapshotListener { snapshot, error ->
				if (error != null) {
					close(error)
					return@addSnapshotListener
				}
				
				val product = snapshot?.toObject(Product::class.java)?.copy(id = snapshot.id)
				trySend(product)
			}
		
		awaitClose { listener.remove() }
	}
}