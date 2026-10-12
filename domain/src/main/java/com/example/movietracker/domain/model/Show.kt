package com.example.movietracker.domain.model

data class Show(
    val id: Int,
    val name: String?,
    val language: String?,
    val genres: List<String>,
    val summary: String?,
    val imageUrl: String?,
    val isFavorite: Boolean = false
)
