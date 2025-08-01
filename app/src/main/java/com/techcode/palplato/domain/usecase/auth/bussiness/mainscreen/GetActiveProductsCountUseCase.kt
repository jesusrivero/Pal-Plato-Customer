package com.techcode.palplato.domain.usecase.auth.bussiness.mainscreen

import com.techcode.palplato.domain.repository.DatesProductsHomeRepository
import kotlinx.coroutines.flow.Flow

class GetActiveProductsCountUseCase(
	private val repository: DatesProductsHomeRepository
) {
	operator fun invoke(businessId: String): Flow<Int> {
		return repository.getActiveProductsCount(businessId)
	}
}