package com.techcode.palplato.domain.repository

import kotlinx.coroutines.flow.Flow

interface DatesProductsHomeRepository {
		fun getActiveProductsCount(businessId: String): Flow<Int>
}