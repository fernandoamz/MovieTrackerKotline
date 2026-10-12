package com.example.movietracker.core.network

import retrofit2.http.GET
import retrofit2.http.Path

interface TvMazeApi {
    @GET("shows")
    suspend fun getShows(): List<ShowDto>

    @GET("shows/{id}")
    suspend fun getShowDetails(@Path("id") id: Int): ShowDto
}
