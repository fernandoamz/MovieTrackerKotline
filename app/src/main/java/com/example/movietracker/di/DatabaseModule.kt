package com.example.movietracker.di

import android.content.Context
import androidx.room.Room
import com.example.movietracker.core.database.AppDatabase
import com.example.movietracker.core.database.FavoriteDao
import com.example.movietracker.core.database.ShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "movie_tracker.db"
        )
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    @Provides
    fun provideShowDao(
        database: AppDatabase
    ): ShowDao {
        return database.showDao()
    }

    @Provides
    fun provideFavoriteDao(
        database: AppDatabase
    ): FavoriteDao {
        return database.favoriteDao()
    }
}
