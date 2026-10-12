package com.example.movietracker.feature.favorites

import com.example.movietracker.domain.model.Show

data class FavoritesUiState(
    val favoriteShows: List<Show> = emptyList(),
    val isLoading: Boolean = true
)
