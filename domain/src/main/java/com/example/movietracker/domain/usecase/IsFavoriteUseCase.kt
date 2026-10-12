package com.example.movietracker.domain.usecase

import com.example.movietracker.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavoriteUseCase @Inject constructor(
    private val repository: ShowRepository
) {
    operator fun invoke(showId: Int): Flow<Boolean> = repository.isFavorite(showId)
}
