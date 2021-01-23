package ru.subbotind.android.academy.myfirstapp.domain.usecase

import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface GetMoviesUseCase {

    suspend fun getMovies(): List<Movie>
}

class GetMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMoviesUseCase {

    override suspend fun getMovies(): List<Movie> = movieRepository.getMovies()
}