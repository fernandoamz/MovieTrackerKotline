package com.example.movietracker.presentation.shows

import com.example.movietracker.domain.model.Show

data class ShowUiState(
    val shows: List<Show> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val error: String? = null,
    val isOffline: Boolean = false
)
