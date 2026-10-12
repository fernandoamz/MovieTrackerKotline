package com.example.movietracker.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_shows")
data class FavoriteShowEntity(
    @PrimaryKey val showId: Int,
    val addedAt: Long = System.currentTimeMillis()
)
