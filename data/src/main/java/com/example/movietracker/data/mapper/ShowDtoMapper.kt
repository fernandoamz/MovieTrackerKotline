package com.example.movietracker.data.mapper

import com.example.movietracker.core.database.ShowEntity
import com.example.movietracker.core.network.ShowDto
import com.example.movietracker.domain.model.Show

fun ShowDto.toEntity(): ShowEntity {
    return ShowEntity(
        id = id,
        name = name,
        language = language,
        genres = genres ?: emptyList(),
        summary = summary,
        imageUrl = image?.medium ?: image?.original
    )
}

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

fun ShowDto.toDomain(): Show {
    return Show(
        id = id,
        name = name,
        language = language,
        genres = genres ?: emptyList(),
        summary = summary,
        imageUrl = image?.medium ?: image?.original
    )
}
