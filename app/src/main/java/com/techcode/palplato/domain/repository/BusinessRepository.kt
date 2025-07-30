package com.techcode.palplato.domain.repository


import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.utils.Resource



interface BusinessRepository {
	suspend fun createBusiness(business: Business): String
	
	suspend fun updateBusinessFields(
		businessId: String,
		updates: Map<String, Any>
	): Resource<Unit>
	suspend fun getBusinessById(businessId: String): Business?
}