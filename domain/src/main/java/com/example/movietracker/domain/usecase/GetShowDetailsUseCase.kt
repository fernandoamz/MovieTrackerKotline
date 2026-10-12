package com.example.movietracker.domain.usecase

import com.example.movietracker.domain.model.Show
import com.example.movietracker.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetShowDetailsUseCase @Inject constructor(
    private val repository: ShowRepository
) {
    operator fun invoke(showId: Int): Flow<Show?> {
        return repository.getShowById(showId)
    }
}
