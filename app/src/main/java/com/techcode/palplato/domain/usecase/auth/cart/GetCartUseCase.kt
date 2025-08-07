package com.techcode.palplato.domain.usecase.auth.cart

import com.techcode.palplato.domain.repository.CartRepository

class GetCartItems(private val repository: CartRepository) {
	operator fun invoke() = repository.getCartItems()
}