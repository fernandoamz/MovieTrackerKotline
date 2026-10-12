package com.example.movietracker.presentation.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietracker.domain.usecase.GetShowDetailsUseCase
import com.example.movietracker.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getShowDetailsUseCase: GetShowDetailsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val showId: Int = checkNotNull(savedStateHandle["showId"])

    val uiState: StateFlow<ShowDetailsUiState> = getShowDetailsUseCase(showId)
        .map { show ->
            if (show != null) {
                ShowDetailsUiState.Success(show)
            } else {
                ShowDetailsUiState.Error("Show not found in local database")
            }
        }
        .catch { e ->
            emit(ShowDetailsUiState.Error(e.localizedMessage ?: "Unknown error occurred"))
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = ShowDetailsUiState.Loading
        )

    fun toggleFavorite() {
        viewModelScope.launch {
            toggleFavoriteUseCase(showId)
        }
    }
}
