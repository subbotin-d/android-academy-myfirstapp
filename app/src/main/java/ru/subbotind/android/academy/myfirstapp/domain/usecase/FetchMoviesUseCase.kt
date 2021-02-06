package ru.subbotind.android.academy.myfirstapp.domain.usecase

import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface FetchMoviesUseCase {

    suspend fun fetchMovies()
}

class FetchMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : FetchMoviesUseCase {

    override suspend fun fetchMovies() = movieRepository.fetchMovies()
}