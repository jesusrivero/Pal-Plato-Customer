package com.techcode.palplato.domain.usecase.auth.bussiness

import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.utils.Resource
import javax.inject.Inject

//class CreateBusinessUseCase @Inject constructor(
//	private val repository: BusinessRepository
//) {
//	suspend operator fun invoke(business: Business): Resource<Unit> {
//		return repository.createBusiness(business)
//	}
//}

class CreateBusinessUseCase @Inject constructor(
	private val businessRepository: BusinessRepository
) {
	suspend operator fun invoke(business: Business): Resource<String> {
		return try {
			val businessId = businessRepository.createBusiness(business) // ✅ Ahora sí devuelve el ID
			Resource.Success(businessId)
		} catch (e: Exception) {
			Resource.Error(e.message ?: "Error al crear negocio")
		}
	}
}