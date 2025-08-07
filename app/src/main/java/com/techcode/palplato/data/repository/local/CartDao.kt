package com.techcode.palplato.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
	@Query("SELECT * FROM cart_items")
	fun getCartItems(): Flow<List<CartItemEntity>>
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCartItem(item: CartItemEntity)
	
	@Query("UPDATE cart_items SET quantity = :quantity WHERE productId = :productId")
	suspend fun updateQuantity(productId: String, quantity: Int)
	
	@Query("SELECT * FROM cart_items WHERE productId = :productId LIMIT 1")
	suspend fun getItemById(productId: String): CartItemEntity?
	
	@Query("DELETE FROM cart_items WHERE productId = :productId")
	suspend fun deleteCartItem(productId: String)
	
	@Query("DELETE FROM cart_items")
	suspend fun clearCart()
}