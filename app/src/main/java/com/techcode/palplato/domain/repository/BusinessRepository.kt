package com.techcode.palplato.domain.repository


import com.techcode.palplato.domain.model.Business
import kotlinx.coroutines.flow.Flow


interface BusinessRepository {
fun getBusiness(businessId: String? = null): Flow<List<Business>>


}