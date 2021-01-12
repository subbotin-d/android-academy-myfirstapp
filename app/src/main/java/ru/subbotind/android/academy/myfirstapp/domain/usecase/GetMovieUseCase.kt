package ru.subbotind.android.academy.myfirstapp.domain.usecase

import android.util.Log
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import javax.inject.Inject

interface GetMovieUseCase {

    suspend fun getMovie(movieId: Int): Movie
}

class GetMovieUseCaseImpl @Inject constructor(
    private val movieRepository: MovieRepository
) : GetMovieUseCase {

    override suspend fun getMovie(movieId: Int): Movie {
        Log.d("viewModel", movieRepository.toString())
        return movieRepository.getMovie(movieId)
    }
}