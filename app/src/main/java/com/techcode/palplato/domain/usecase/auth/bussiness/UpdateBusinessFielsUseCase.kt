package com.techcode.palplato.domain.usecase.auth.bussiness

import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.utils.Resource
import javax.inject.Inject

//class UpdateBusinessFieldsUseCase @Inject constructor(
//	private val repository: BusinessRepository
//) {
//	suspend operator fun invoke(
//		businessId: String,
//		updates: Map<String, Any>
//	): Resource<Unit> {
//		return repository.updateBusinessFields(businessId, updates)
//	}
//}

class UpdateBusinessUseCase @Inject constructor(
	private val businessRepository: BusinessRepository
) {
	suspend operator fun invoke(businessId: String, updates: Map<String, Any>): Resource<Unit> {
		return businessRepository.updateBusinessFields(businessId, updates)
	}
}
