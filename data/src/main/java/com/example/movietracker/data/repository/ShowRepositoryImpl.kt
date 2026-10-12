package com.example.movietracker.data.repository

import com.example.movietracker.core.database.FavoriteDao
import com.example.movietracker.core.database.FavoriteShowEntity
import com.example.movietracker.core.database.ShowDao
import com.example.movietracker.core.network.TvMazeApi
import com.example.movietracker.data.mapper.toDomain
import com.example.movietracker.data.mapper.toEntity
import com.example.movietracker.domain.model.Show
import com.example.movietracker.domain.repository.ShowRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShowRepositoryImpl @Inject constructor(
    private val api: TvMazeApi,
    private val dao: ShowDao,
    private val favoriteDao: FavoriteDao
) : ShowRepository {

    override fun getShows(): Flow<List<Show>> {
        return combine(
            dao.getShows(),
            favoriteDao.getFavoriteShowIds()
        ) { entities, favoriteIds ->
            val favoriteSet = favoriteIds.toSet()
            entities.map { entity ->
                entity.toDomain().copy(isFavorite = entity.id in favoriteSet)
            }
        }
    }

    override fun getShowById(id: Int): Flow<Show?> {
        return combine(
            dao.getShowById(id),
            favoriteDao.isFavorite(id)
        ) { entity, isFav ->
            entity?.toDomain()?.copy(isFavorite = isFav)
        }
    }

    override fun getFavoriteShows(): Flow<List<Show>> {
        return favoriteDao.getFavoriteShows().map { entities ->
            entities.map { it.toDomain().copy(isFavorite = true) }
        }
    }

    override fun isFavorite(showId: Int): Flow<Boolean> {
        return favoriteDao.isFavorite(showId)
    }

    override suspend fun toggleFavorite(showId: Int) {
        val currentlyFav = favoriteDao.isFavorite(showId).first()
        if (currentlyFav) {
            favoriteDao.deleteFavorite(showId)
        } else {
            favoriteDao.insertFavorite(FavoriteShowEntity(showId = showId))
        }
    }

    override suspend fun refreshShows(): Result<Unit> {
        return runCatching {
            val remoteDtos = api.getShows()
            val entities = remoteDtos.map { it.toEntity() }
            dao.upsertShows(entities)
        }
    }

    override suspend fun refreshShowDetails(id: Int): Result<Unit> {
        return runCatching {
            val remoteDto = api.getShowDetails(id)
            dao.upsertShow(remoteDto.toEntity())
        }
    }
}
