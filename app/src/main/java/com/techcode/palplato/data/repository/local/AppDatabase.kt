package com.techcode.palplato.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.techcode.palplato.domain.model.room.FavoriteProduct

@Database(
	entities = [
		CartItemEntity::class,
		FavoriteBusinessEntity::class,
		FavoriteProduct::class
	
	],
	version = 4, // si es la primera vez que la incluyes, sube este número
	exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
	abstract fun cartDao(): CartDao
	abstract fun favoriteBusinessDao(): FavoriteBusinessDao
	abstract fun favoriteProductDao(): FavoriteProductDao
}

//@Database(
//	entities = [CartItemEntity::class],
//	version = 1,
//	exportSchema = false
//)
//abstract class AppDatabase : RoomDatabase() {
//	abstract fun cartDao(): CartDao
//}