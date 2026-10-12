package com.example.movietracker.domain.usecase

import com.example.movietracker.domain.repository.ShowRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: ShowRepository
) {
    suspend operator fun invoke(showId: Int) {
        repository.toggleFavorite(showId)
    }
}
