package com.example.movietracker.di

import com.example.movietracker.data.repository.ShowRepositoryImpl
import com.example.movietracker.domain.repository.ShowRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ShowRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindShowRepository(
        showRepositoryImpl: ShowRepositoryImpl
    ): ShowRepository
}
