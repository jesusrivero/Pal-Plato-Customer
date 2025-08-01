package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.local.SessionManager
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.model.BusinessSchedule
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import com.techcode.palplato.domain.model.Category

class BusinessRepositoryImpl @Inject constructor(
	private val firestore: FirebaseFirestore,
	private val sessionManager: SessionManager,
) : BusinessRepository {
	
	
	override suspend fun createBusiness(business: Business): String {
		val docRef = firestore.collection("businesses").document()
		val newBusiness = business.copy(businessId = docRef.id)
		
		// Guardar negocio en Firestore
		docRef.set(newBusiness).await()
		
		// Obtener el UID del usuario actual
		val userId = sessionManager.getUserUid()
			?: throw Exception("Usuario no autenticado")
		
		// Actualizar el documento del usuario con el businessId
		firestore.collection("users")
			.document(userId)
			.update(
				mapOf(
					"businessId" to docRef.id,
					"businessName" to newBusiness.name
				)
			).await()
		
		return docRef.id // ✅ Retornar businessId
	}

	
//	override suspend fun createBusiness(business: Business): String {
//		val docRef = firestore.collection("businesses").document()
//		val newBusiness = business.copy(businessId = docRef.id)
//		docRef.set(newBusiness).await()
//		return docRef.id // ✅ Retornamos el businessId
//	}
	
	
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
		
		// 🔥 Conversión manual de horarios (schedule)
		val scheduleList = (snapshot["schedule"] as? List<Map<String, Any>>)?.map { scheduleMap ->
			BusinessSchedule(
				day = scheduleMap["day"] as? String ?: "",
				openTime = scheduleMap["openTime"] as? String,
				closeTime = scheduleMap["closeTime"] as? String,
				isOpen = scheduleMap["isOpen"] as? Boolean ?: false // 👈 Se lee directamente de Firestore
			)
		} ?: emptyList()
		
		return rawBusiness.copy(
			businessId = snapshot.id,
			categories = categoriesList,
			schedule = scheduleList // 👈 Se reemplaza el schedule
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