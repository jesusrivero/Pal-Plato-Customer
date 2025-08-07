package com.techcode.palplato.domain.usecase.auth.cart

import com.techcode.palplato.domain.model.CartItem
import com.techcode.palplato.domain.repository.CartRepository

class AddToCart(private val repository: CartRepository) {
	suspend operator fun invoke(item: CartItem) {
		repository.addOrUpdateItem(item)
	}
}