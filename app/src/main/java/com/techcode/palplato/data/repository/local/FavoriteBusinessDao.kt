package com.techcode.palplato.data.repository.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteBusinessDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertFavoriteBusiness(business: FavoriteBusinessEntity)
	
	@Query("DELETE FROM favorite_businesses WHERE businessId = :businessId")
	suspend fun deleteFavoriteBusinessById(businessId: String)
	
	@Delete
	suspend fun deleteFavoriteBusiness(business: FavoriteBusinessEntity)
	
	@Query("DELETE FROM favorite_businesses WHERE businessId = :businessId")
	suspend fun deleteFavoriteById(businessId: String)
	
	@Query("SELECT * FROM favorite_businesses")
	fun getAllFavoriteBusinesses(): Flow<List<FavoriteBusinessEntity>>
	
	@Query("SELECT EXISTS(SELECT 1 FROM favorite_businesses WHERE businessId = :businessId)")
	suspend fun isBusinessFavorite(businessId: String): Boolean
}