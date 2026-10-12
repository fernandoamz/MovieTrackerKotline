package com.example.movietracker.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT EXISTS(SELECT 1 FROM favorite_shows WHERE showId = :showId)")
    fun isFavorite(showId: Int): Flow<Boolean>

    @Query("SELECT showId FROM favorite_shows")
    fun getFavoriteShowIds(): Flow<List<Int>>

    @Query("SELECT shows.* FROM shows INNER JOIN favorite_shows ON shows.id = favorite_shows.showId ORDER BY favorite_shows.addedAt DESC")
    fun getFavoriteShows(): Flow<List<ShowEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteShowEntity)

    @Query("DELETE FROM favorite_shows WHERE showId = :showId")
    suspend fun deleteFavorite(showId: Int)
}
