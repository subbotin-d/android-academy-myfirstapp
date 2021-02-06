package ru.subbotind.android.academy.myfirstapp.domain.usecase

import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface FetchMovieUseCase {

    suspend fun fetchMovie(movieId: Int)
}

class FetchMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : FetchMovieUseCase {

    override suspend fun fetchMovie(movieId: Int) {
        movieRepository.fetchMovie(movieId)
    }
}