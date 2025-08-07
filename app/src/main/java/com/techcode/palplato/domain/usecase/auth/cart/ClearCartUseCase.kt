package com.techcode.palplato.domain.usecase.auth.cart

import com.techcode.palplato.domain.repository.CartRepository

class ClearCart(private val repository: CartRepository) {
	suspend operator fun invoke() {
		repository.clearCart()
	}
}