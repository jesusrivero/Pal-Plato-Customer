package com.techcode.palplato.domain.repository

import com.techcode.palplato.domain.model.CartItem
import kotlinx.coroutines.flow.Flow

interface CartRepository {
	fun getCartItems(): Flow<List<CartItem>>
	suspend fun addOrUpdateItem(item: CartItem)
	suspend fun removeItem(productId: String)
	suspend fun clearCart()
	suspend fun updateQuantity(productId: String, quantity: Int)
	suspend fun completePurchase(userId: String)
}