package ru.subbotind.android.academy.myfirstapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.subbotind.android.academy.myfirstapp.data.dao.MovieDao
import ru.subbotind.android.academy.myfirstapp.data.database.MovieDataBase
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieLocalDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieLocalDataSourceImpl
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSourceImpl
import ru.subbotind.android.academy.myfirstapp.data.network.MovieService
import ru.subbotind.android.academy.myfirstapp.data.repository.MovieRepositoryImpl
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import ru.subbotind.android.academy.myfirstapp.domain.usecase.*
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface MoviesModule {

    companion object {
        @Provides
        @Singleton
        fun provideMovieService(retrofit: Retrofit): MovieService =
            retrofit.create()

        @Provides
        @Singleton
        fun provideMovieDao(movieDataBase: MovieDataBase): MovieDao =
            movieDataBase.movieDao()
    }

    @Binds
    @Singleton
    fun bindMovieRemoteDataSource(moveDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    @Singleton
    fun bindMovieLocalDataSource(moveDataSource: MovieLocalDataSourceImpl): MovieLocalDataSource

    @Binds
    @Singleton
    fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Reusable
    fun bindGetMoviesUseCase(getMoviesUseCase: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds
    @Reusable
    fun bindGetMovieUseCase(getMovieUseCase: GetMovieUseCaseImpl): GetMovieUseCase

    @Binds
    @Reusable
    fun bindFetchMoviesUseCase(fetchMoviesUseCase: FetchMoviesUseCaseImpl): FetchMoviesUseCase

    @Binds
    @Reusable
    fun bindFetchMovieUseCase(fetchMovieUseCase: FetchMovieUseCaseImpl): FetchMovieUseCase
}