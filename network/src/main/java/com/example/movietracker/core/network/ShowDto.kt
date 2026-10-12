package com.example.movietracker.core.network

data class ShowDto(
    val id: Int,
    val name: String?,
    val language: String?,
    val genres: List<String>?,
    val summary: String?,
    val image: ImageDto?
)

data class ImageDto(
    val medium: String?,
    val original: String?
)
