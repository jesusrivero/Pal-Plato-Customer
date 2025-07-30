package com.techcode.palplato.domain.usecase.auth.bussiness

import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.repository.BusinessRepository
import javax.inject.Inject

class GetBusinessUseCase @Inject constructor(
	private val businessRepository: BusinessRepository
) {
	suspend operator fun invoke(businessId: String): Business? {
		return businessRepository.getBusinessById(businessId)
	}
}