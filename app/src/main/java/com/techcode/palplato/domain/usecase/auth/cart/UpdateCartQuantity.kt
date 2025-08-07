package com.techcode.palplato.domain.usecase.auth.cart

import com.techcode.palplato.domain.repository.CartRepository

class UpdateCartQuantity(
	private val repository: CartRepository
) {
	suspend operator fun invoke(productId: String, quantity: Int) {
		repository.updateQuantity(productId, quantity)
	}
}