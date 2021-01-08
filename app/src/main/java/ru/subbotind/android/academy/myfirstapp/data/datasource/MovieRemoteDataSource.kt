package ru.subbotind.android.academy.myfirstapp.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.subbotind.android.academy.myfirstapp.data.model.DetailedMovieModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieCreditsModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieModel
import ru.subbotind.android.academy.myfirstapp.data.network.MovieService
import javax.inject.Inject

interface MovieRemoteDataSource {

    suspend fun getMovies(): List<MovieModel>

    suspend fun getMovie(movieId: Int): DetailedMovieModel

    suspend fun getMovieActors(movieId: Int): MovieCreditsModel
}

class MovieRemoteDataSourceImpl @Inject constructor(
    private val movieService: MovieService
) : MovieRemoteDataSource {

    override suspend fun getMovies(): List<MovieModel> = withContext(Dispatchers.IO) {
        movieService.getMovies().results
    }

    override suspend fun getMovie(movieId: Int): DetailedMovieModel = withContext(Dispatchers.IO) {
        movieService.getMovie(movieId)
    }

    override suspend fun getMovieActors(movieId: Int): MovieCreditsModel =
        withContext(Dispatchers.IO) {
            movieService.getMovieCredits(movieId)
        }
}