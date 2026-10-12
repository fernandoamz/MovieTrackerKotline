package com.example.movietracker.core.database

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromGenres(value: List<String>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toGenres(value: String): List<String> {
        return value.split(",")
    }
}
