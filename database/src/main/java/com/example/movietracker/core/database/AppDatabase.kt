package com.example.movietracker.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [ShowEntity::class, FavoriteShowEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun showDao(): ShowDao
    abstract fun favoriteDao(): FavoriteDao
}

