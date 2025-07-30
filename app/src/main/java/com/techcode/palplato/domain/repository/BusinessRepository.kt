package com.techcode.palplato.domain.repository


import com.techcode.palplato.domain.model.Business
import com.techcode.palplato.utils.Resource



interface BusinessRepository {
	suspend fun createBusiness(business: Business): Resource<Unit>

}