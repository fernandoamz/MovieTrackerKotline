package com.example.movietracker.domain.repository

import com.example.movietracker.domain.model.Show
import kotlinx.coroutines.flow.Flow

interface ShowRepository {
    fun getShows(): Flow<List<Show>>
    fun getShowById(id: Int): Flow<Show?>
    fun getFavoriteShows(): Flow<List<Show>>
    fun isFavorite(showId: Int): Flow<Boolean>
    suspend fun toggleFavorite(showId: Int)
    suspend fun refreshShows(): Result<Unit>
    suspend fun refreshShowDetails(id: Int): Result<Unit>
}
