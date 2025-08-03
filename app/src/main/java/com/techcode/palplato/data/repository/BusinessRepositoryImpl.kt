package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.model.BusinessSchedule

import javax.inject.Inject
import com.techcode.palplato.domain.model.Category
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class BusinessRepositoryImpl @Inject constructor(
	private val firestore: FirebaseFirestore
) : BusinessRepository {
	
	override fun getBusiness(businessId: String?): Flow<List<Business>> = callbackFlow {
		var query = firestore.collection("businesses")
			.whereEqualTo("state", true)
		
		if (businessId != null) {
			query = firestore.collection("businesses")
				.whereEqualTo("state", true)
				.whereEqualTo(FieldPath.documentId(), businessId)
		}
		
		val listener = query.addSnapshotListener { snapshot, error ->
			if (error != null) {
				close(error)
				return@addSnapshotListener
			}
			
			val businesses = snapshot?.documents?.mapNotNull { doc ->
				val data = doc.data ?: return@mapNotNull null
				
				val scheduleList = (data["schedule"] as? List<Map<String, Any>>)?.map { scheduleItem ->
					BusinessSchedule(
						day = scheduleItem["day"] as? String ?: "",
						openTime = scheduleItem["openTime"] as? String,
						closeTime = scheduleItem["closeTime"] as? String,
						isOpen = scheduleItem["isOpen"] as? Boolean ?: false
					)
				} ?: emptyList()
				
				Business(
					id = doc.id,
					ownerId = data["ownerId"] as? String ?: "",
					name = data["name"] as? String ?: "",
					direction = data["direction"] as? String ?: "",
					phone = data["phone"] as? String ?: "",
					description = data["description"] as? String ?: "",
					state = data["state"] as? Boolean ?: true,
					products = data["products"] as? List<String> ?: emptyList(),
					date = data["date"] as? Long ?: System.currentTimeMillis(),
					logoUrl = data["logoUrl"] as? String,
					isOpen = data["isOpen"] as? Boolean ?: false,
					categories = (data["categories"] as? List<Map<String, Any>>)?.map {
						Category(
							name = it["name"] as? String ?: "",
							products = it["products"] as? List<String> ?: emptyList()
						)
					} ?: emptyList(),
					schedule = scheduleList
				)
			} ?: emptyList()
			
			trySend(businesses)
		}
		
		awaitClose { listener.remove() }
	}



}