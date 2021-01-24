package ru.subbotind.android.academy.myfirstapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface GetMoviesUseCase {

    fun getMovies(): Flow<List<Movie>>
}

class GetMoviesUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMoviesUseCase {

    override fun getMovies(): Flow<List<Movie>> = movieRepository.getMovies()
}