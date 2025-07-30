package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.techcode.palplato.domain.model.Category

class BusinessRepositoryImpl @Inject constructor(
	private val firestore: FirebaseFirestore
) : BusinessRepository {
	
	
	override suspend fun createBusiness(business: Business): String {
		val docRef = firestore.collection("businesses").document()
		val newBusiness = business.copy(businessId = docRef.id)
		docRef.set(newBusiness).await()
		return docRef.id // ✅ Retornamos el businessId
	}
	
	
	override suspend fun getBusinessById(businessId: String): Business? {
		val snapshot = firestore.collection("businesses")
			.document(businessId)
			.get()
			.await()
		
		if (!snapshot.exists()) return null
		
		val rawBusiness = snapshot.toObject(Business::class.java) ?: return null
		
		// 🔥 Conversión manual de categorías
		val categoriesList = (snapshot["categories"] as? List<Map<String, Any>>)?.map { categoryMap ->
			Category(
				name = categoryMap["name"] as? String ?: "",
				products = (categoryMap["products"] as? List<String>) ?: emptyList()
			)
		} ?: emptyList()
		
		return rawBusiness.copy(
			businessId = snapshot.id,
			categories = categoriesList
		)
	}



//	override suspend fun getBusinessById(businessId: String): Business? {
//		val snapshot = firestore.collection("businesses")
//			.document(businessId)
//			.get()
//			.await()
//		return snapshot.toObject(Business::class.java)?.copy(businessId = snapshot.id)
//	}
	
	
	override suspend fun updateBusinessFields(
		businessId: String,
		updates: Map<String, Any>
	): Resource<Unit> {
		return try {
			firestore.collection("businesses")
				.document(businessId)
				.update(updates)
				.await()
			Resource.Success(Unit)
		} catch (e: Exception) {
			Resource.Error(e.message ?: "Error al actualizar el negocio")
		}
	}
	
}