package com.example.movietracker.core.database.mapper

import com.example.movietracker.core.database.ShowEntity
import com.example.movietracker.domain.model.Show

fun ShowEntity.toDomain(): Show {
    return Show(
        id = id,
        name = name,
        language = language,
        genres = genres,
        summary = summary,
        imageUrl = imageUrl
    )
}

fun Show.toEntity(): ShowEntity {
    return ShowEntity(
        id = id,
        name = name,
        language = language,
        genres = genres,
        summary = summary,
        imageUrl = imageUrl
    )
}