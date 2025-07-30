package com.techcode.palplato.domain.usecase.auth.bussiness

import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.domain.repository.BusinessRepository
import com.techcode.palplato.utils.Resource
import javax.inject.Inject

class CreateBusinessUseCase @Inject constructor(
	private val repository: BusinessRepository
) {
	suspend operator fun invoke(business: Business): Resource<Unit> {
		return repository.createBusiness(business)
	}
}