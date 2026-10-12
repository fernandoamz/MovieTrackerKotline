package com.example.movietracker.core.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    @Query("SELECT * FROM shows")
    fun getShows(): Flow<List<ShowEntity>>

    @Query("SELECT * FROM shows WHERE id = :id")
    fun getShowById(id: Int): Flow<ShowEntity?>

    @Query("SELECT * FROM shows")
    fun observeShows(): Flow<List<ShowEntity>>

    @Upsert
    suspend fun upsertShows(shows: List<ShowEntity>)

    @Upsert
    suspend fun upsertShow(show: ShowEntity)

    @Query("DELETE FROM shows")
    suspend fun deleteAll()
}