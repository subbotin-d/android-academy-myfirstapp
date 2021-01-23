package ru.subbotind.android.academy.myfirstapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSourceImpl
import ru.subbotind.android.academy.myfirstapp.data.network.MovieService
import ru.subbotind.android.academy.myfirstapp.data.repository.MovieRepositoryImpl
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMovieUseCaseImpl
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMoviesUseCase
import ru.subbotind.android.academy.myfirstapp.domain.usecase.GetMoviesUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
interface MoviesModule {

    companion object {
        @Provides
        @Singleton
        fun provideMovieService(retrofit: Retrofit): MovieService =
            retrofit.create()
    }

    @Binds
    @Singleton
    fun bindMovieRemoteDataSource(moveDataSource: MovieRemoteDataSourceImpl): MovieRemoteDataSource

    @Binds
    @Singleton
    fun bindMovieRepository(movieRepository: MovieRepositoryImpl): MovieRepository

    @Binds
    @Reusable
    fun bindGetMoviesUseCase(getMoviesUseCase: GetMoviesUseCaseImpl): GetMoviesUseCase

    @Binds
    @Reusable
    fun bindGetMovieUseCase(getMovieUseCase: GetMovieUseCaseImpl): GetMovieUseCase
}