package com.techcode.palplato.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.techcode.palplato.domain.model.room.FavoriteProduct

@Dao
interface FavoriteProductDao {
	@Query("SELECT * FROM favorite_products")
	suspend fun getAllFavorites(): List<FavoriteProduct>
	
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun addFavorite(product: FavoriteProduct)
	
	@Delete
	suspend fun removeFavorite(product: FavoriteProduct)
	
	@Query("SELECT EXISTS(SELECT 1 FROM favorite_products WHERE id = :productId)")
	suspend fun isFavorite(productId: String): Boolean
}