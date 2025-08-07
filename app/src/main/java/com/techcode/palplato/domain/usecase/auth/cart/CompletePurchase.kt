package com.techcode.palplato.domain.usecase.auth.cart

import com.techcode.palplato.domain.repository.CartRepository

class CompletePurchase(private val repository: CartRepository) {
	suspend operator fun invoke(userId: String) {
		repository.completePurchase(userId)
	}
}