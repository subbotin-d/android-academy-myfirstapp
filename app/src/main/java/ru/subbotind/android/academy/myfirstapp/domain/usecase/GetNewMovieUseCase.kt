package ru.subbotind.android.academy.myfirstapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface GetNewMovieUseCase {

    fun getNewMovie(): Flow<Movie?>
}

class GetNewMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetNewMovieUseCase {

    override fun getNewMovie(): Flow<Movie?> =
        movieRepository.getNewMovies().mapLatest { movies ->
            movies.maxByOrNull { it.ratings }
        }
}