package com.example.movietracker.presentation.details

import com.example.movietracker.domain.model.Show

sealed interface ShowDetailsUiState {
    data object Loading : ShowDetailsUiState
    data class Success(val show: Show) : ShowDetailsUiState
    data class Error(val message: String) : ShowDetailsUiState
}
