package com.example.movietracker.feature.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietracker.domain.usecase.GetFavoriteShowsUseCase
import com.example.movietracker.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteShowsUseCase: GetFavoriteShowsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    val uiState: StateFlow<FavoritesUiState> = getFavoriteShowsUseCase()
        .map { shows ->
            FavoritesUiState(
                favoriteShows = shows,
                isLoading = false
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesUiState(isLoading = true)
        )

    fun toggleFavorite(showId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(showId)
        }
    }
}
