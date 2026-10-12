package com.example.movietracker.domain.usecase

import com.example.movietracker.domain.repository.ShowRepository
import javax.inject.Inject

class RefreshShowsUseCase @Inject constructor(
    private val showRepository: ShowRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return showRepository.refreshShows()
    }
}
