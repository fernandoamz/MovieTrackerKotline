package com.example.movietracker.presentation.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movietracker.domain.usecase.GetShowsUseCase
import com.example.movietracker.domain.usecase.RefreshShowsUseCase
import com.example.movietracker.domain.usecase.ToggleFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    getShowsUseCase: GetShowsUseCase,
    private val refreshShowsUseCase: RefreshShowsUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : ViewModel() {

    private val _isRefreshing = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    val uiState: StateFlow<ShowUiState> = combine(
        getShowsUseCase(),
        _isRefreshing,
        _errorMessage
    ) { shows, isRefreshing, error ->
        ShowUiState(
            shows = shows,
            isLoading = shows.isEmpty() && error == null,
            isRefreshing = isRefreshing,
            error = error,
            isOffline = error != null && shows.isNotEmpty()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = ShowUiState(isLoading = true, isRefreshing = false)
    )

    init {
        refreshShows()
    }

    fun refreshShows() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _errorMessage.value = null

            refreshShowsUseCase()
                .onSuccess {
                    _errorMessage.value = null
                }
                .onFailure { exception ->
                    _errorMessage.value = exception.localizedMessage ?: "Error de red al actualizar"
                }

            _isRefreshing.value = false
        }
    }

    fun toggleFavorite(showId: Int) {
        viewModelScope.launch {
            toggleFavoriteUseCase(showId)
        }
    }
}
