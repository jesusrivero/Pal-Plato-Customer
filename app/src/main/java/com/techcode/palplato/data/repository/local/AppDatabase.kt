package com.techcode.palplato.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CartItemEntity::class], version = 2, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
	abstract fun cartDao(): CartDao
}

//@Database(
//	entities = [CartItemEntity::class],
//	version = 1,
//	exportSchema = false
//)
//abstract class AppDatabase : RoomDatabase() {
//	abstract fun cartDao(): CartDao
//}