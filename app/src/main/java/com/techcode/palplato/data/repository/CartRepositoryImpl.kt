package com.techcode.palplato.data.repository

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.techcode.palplato.data.repository.local.CartDao
import com.techcode.palplato.data.repository.local.CartItemEntity
import com.techcode.palplato.domain.model.CartItem
import com.techcode.palplato.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
	private val cartDao: CartDao,
	private val firestore: FirebaseFirestore
) : CartRepository {
	
	override fun getCartItems(): Flow<List<CartItem>> {
		return cartDao.getCartItems().map { entities ->
			entities.map { it.toDomain() }
		}
	}
	
	override suspend fun addOrUpdateItem(item: CartItem) {
		val existingItem = cartDao.getItemById(item.productId)
		if (existingItem != null) {
			cartDao.updateQuantity(item.productId, existingItem.quantity + item.quantity)
		} else {
			cartDao.insertCartItem(item.toEntity())
		}
	}
	
	override suspend fun removeItem(productId: String) {
		cartDao.deleteCartItem(productId)
	}
	
	override suspend fun clearCart() {
		cartDao.clearCart()
	}
	
	override suspend fun completePurchase(userId: String) {
		val items = cartDao.getCartItems().map { it.map { entity -> entity.toDomain() } }
			.firstOrNull() ?: emptyList()
		if (items.isEmpty()) return
		
		val businessId = items.first().businessId
		val total = items.sumOf { it.price * it.quantity }
		
		val order = hashMapOf(
			"userId" to userId,
			"items" to items.map {
				mapOf(
					"productId" to it.productId,
					"productName" to it.productName,
					"quantity" to it.quantity,
					"price" to it.price
				)
			},
			"total" to total,
			"status" to "pending",
			"timestamp" to FieldValue.serverTimestamp()
		)
		
		firestore.collection("businesses")
			.document(businessId)
			.collection("pedidos")
			.add(order)
		
		cartDao.clearCart()
	}
	
	override suspend fun updateQuantity(productId: String, quantity: Int) =
		cartDao.updateQuantity(productId, quantity)
	
	
	private fun CartItemEntity.toDomain() = CartItem(
		productId = productId,
		productName = productName,
		price = price,
		quantity = quantity,
		businessId = businessId,
		imageUrl = imageUrl
	)
	
	private fun CartItem.toEntity() = CartItemEntity(
		productId = productId,
		productName = productName,
		price = price,
		quantity = quantity,
		businessId = businessId,
		imageUrl = imageUrl
	)
}