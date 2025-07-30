package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.utils.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BusinessRepositoryImpl @Inject constructor(
	private val firestore: FirebaseFirestore
) : BusinessRepository {
	
	
	override suspend fun createBusiness(business: Business): Resource<Unit> {
		return try {
			val docRef = firestore.collection("businesses").document()
			val businessWithId = business.copy(businessId = docRef.id)
			docRef.set(businessWithId).await()
			Resource.Success(Unit)
		} catch (e: Exception) {
			Resource.Error(e.message ?: "Error al crear el negocio")
		}
	}
	
//	private suspend fun uploadImageToStorage(imageUri: Uri, fileName: String): Resource<String> {
//		return try {
//			val storageRef = Firebase.storage.reference.child(fileName)
//			storageRef.putFile(imageUri).await()
//			val downloadUrl = storageRef.downloadUrl.await()
//			Resource.Success(downloadUrl.toString())
//		} catch (e: Exception) {
//			Resource.Error(e.message ?: "Error al subir la imagen")
//		}
//	}


//	override suspend fun createBusiness(business: Business): Resource<Unit> {
//		return try {
//			// 1️⃣ Crear referencia para el nuevo negocio
//			val docRef = firestore.collection("businesses").document()
//			val businessWithId = business.copy(businessId = docRef.id)
//
//			// 2️⃣ Ejecutar operaciones en batch (atómicas)
//			firestore.runBatch { batch ->
//				// Guardar el negocio
//				batch.set(docRef, businessWithId)
//
//				// Actualizar el documento del dueño con el businessId
//				val ownerRef = firestore.collection("users").document(business.ownerId)
//				batch.update(ownerRef, "businessId", docRef.id)
//			}.await()
//
//			Resource.Success(Unit)
//		} catch (e: Exception) {
//			Resource.Error(e.message ?: "Error al crear el negocio")
//		}
//	}

}