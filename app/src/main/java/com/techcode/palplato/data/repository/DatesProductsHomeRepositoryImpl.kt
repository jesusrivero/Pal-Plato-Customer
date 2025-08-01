package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.domain.repository.DatesProductsHomeRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class DatesProductsHomeRepositoryImpl(
	private val firestore: FirebaseFirestore
) : DatesProductsHomeRepository {
	
	override fun getActiveProductsCount(businessId: String): Flow<Int> = callbackFlow {
		val listener = firestore.collection("businesses")
			.document(businessId)
			.collection("products")
			.whereEqualTo("available", true)
			.addSnapshotListener { snapshot, error ->
				if (error != null) {
					close(error)
					return@addSnapshotListener
				}
				trySend(snapshot?.size() ?: 0)
			}
		
		awaitClose { listener.remove() }
	}
}
