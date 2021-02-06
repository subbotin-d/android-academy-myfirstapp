package ru.subbotind.android.academy.myfirstapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface GetMovieUseCase {

    fun getMovie(movieId: Int): Flow<Movie>
}

class GetMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieUseCase {

    override fun getMovie(movieId: Int): Flow<Movie> = movieRepository.getMovie(movieId)
}