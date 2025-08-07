package com.techcode.palplato.domain.usecase.auth.cart

import com.techcode.palplato.domain.repository.CartRepository

class RemoveFromCart(private val repository: CartRepository) {
	suspend operator fun invoke(productId: String) {
		repository.removeItem(productId)
	}
}