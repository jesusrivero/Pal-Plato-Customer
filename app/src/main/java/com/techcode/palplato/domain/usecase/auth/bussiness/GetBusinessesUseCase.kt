package com.techcode.palplato.domain.usecase.auth.bussiness

import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.repository.BusinessRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBusinessesUseCase @Inject constructor(
	private val repository: BusinessRepository
) {
	operator fun invoke(businessId: String? = null): Flow<List<Business>> {
		return repository.getBusiness(businessId)
	}
}

//class GetBusinessesUseCase @Inject constructor(
//	private val repository: BusinessRepository
//) {
//	operator fun invoke(): Flow<List<Business>> {
//		return repository.getBusiness()
//	}
//}
