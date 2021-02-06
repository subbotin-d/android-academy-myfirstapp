package ru.subbotind.android.academy.myfirstapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.subbotind.android.academy.myfirstapp.data.datasource.GenreRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.GenreRemoteDataSourceIml
import ru.subbotind.android.academy.myfirstapp.data.network.GenreService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface GenreModule {

    companion object {
        @Provides
        @Singleton
        fun provideGenreService(retrofit: Retrofit): GenreService = retrofit.create()
    }

    @Binds
    @Singleton
    fun bindGenreRemoteDataSource(genreRemoteDataSource: GenreRemoteDataSourceIml): GenreRemoteDataSource
}