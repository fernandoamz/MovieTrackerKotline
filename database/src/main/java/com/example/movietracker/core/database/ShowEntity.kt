package com.example.movietracker.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shows")
data class ShowEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val language: String?,
    val genres: List<String>,
    val summary: String?,
    val imageUrl: String?
)
