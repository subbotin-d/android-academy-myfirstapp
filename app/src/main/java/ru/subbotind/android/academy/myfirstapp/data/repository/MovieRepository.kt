package ru.subbotind.android.academy.myfirstapp.data.repository

import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.academy.myfirstapp.data.datasource.GenreRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieLocalDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.MovieRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.model.DetailedMovieModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieModel
import ru.subbotind.android.academy.myfirstapp.data.repository.converter.MovieConverter
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import ru.subbotind.android.academy.myfirstapp.domain.repository.MovieRepository
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieRemoteDataSource: MovieRemoteDataSource,
    private val movieLocalDataSource: MovieLocalDataSource,
    private val baseImageUrlRepository: BaseImageUrlRepository,
    private val genresDataSource: GenreRemoteDataSource,
    private val movieConverter: MovieConverter
) : MovieRepository {

    override fun getMovies(): Flow<List<Movie>> = movieLocalDataSource.getMovies()

    override suspend fun fetchMovies() {
        try {
            val moviesFromNetwork = getMoviesFromNetwork()
            movieLocalDataSource.refreshMovies(moviesFromNetwork)
        } catch (e: SocketTimeoutException) {
            //do nothing
        } catch (e: ConnectException) {
            //do nothing
        } catch (e: UnknownHostException) {
            //do nothing
        }
    }

    private suspend fun getMoviesFromNetwork(): List<Movie> {
        val movieResults: List<MovieModel> = movieRemoteDataSource.getMovies()
        val baseImageUrl: String = baseImageUrlRepository.getBaseUrl()
        val genresMap: Map<Int, Genre> = genresDataSource.getGenres().associateBy { it.id }

        return movieResults.map { movieModel ->
            movieConverter.toMovie(
                movieModel,
                baseImageUrl,
                genresMap
            )
        }
    }

    override fun getMovie(movieId: Int): Flow<Movie> = movieLocalDataSource.getMovie(movieId)

    override suspend fun fetchMovie(movieId: Int) {
        try {
            val moviesFromNetwork = getMovieFromNetwork(movieId)
            movieLocalDataSource.updateMovie(moviesFromNetwork)
        } catch (e: SocketTimeoutException) {
            //do nothing
        } catch (e: ConnectException) {
            //do nothing
        } catch (e: UnknownHostException) {
            //do nothing
        }
    }

    private suspend fun getMovieFromNetwork(movieId: Int): Movie {
        val movieModel: DetailedMovieModel = movieRemoteDataSource.getMovie(movieId)
        val baseImageUrl: String = baseImageUrlRepository.getBaseUrl()

        val actors: List<Actor> = getMovieActors(movieId)
        val actorsWithCorrectImageUrl: List<Actor> = updateActorImageUrls(actors, baseImageUrl)

        return movieConverter.toMovie(movieModel, baseImageUrl, actorsWithCorrectImageUrl)
    }

    private suspend fun getMovieActors(movieId: Int): List<Actor> =
        movieRemoteDataSource.getMovieActors(movieId).cast

    private fun updateActorImageUrls(actors: List<Actor>, baseImageUrl: String): List<Actor> =
        actors.map { actor ->
            actor.copy(
                picture = actor.picture?.let { "$baseImageUrl${it}" }
            )
        }
}