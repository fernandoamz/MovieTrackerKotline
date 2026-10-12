package com.example.movietracker.core.network
import com.example.movietracker.domain.model.Show

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
