package ru.subbotind.android.academy.myfirstapp.data.datasource

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import ru.subbotind.android.academy.myfirstapp.data.converter.MovieEntityConverter
import ru.subbotind.android.academy.myfirstapp.data.dao.MovieDao
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie
import javax.inject.Inject

interface MovieLocalDataSource {

    fun getMovies(): Flow<List<Movie>>

    fun getMovie(movieId: Int): Flow<Movie>

    suspend fun refreshMovies(movies: List<Movie>)

    suspend fun updateMovie(movie: Movie)
}

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao,
    private val movieEntityConverter: MovieEntityConverter
) : MovieLocalDataSource {

    override fun getMovies(): Flow<List<Movie>> =
        movieDao.getMovies().mapLatest { movieEntityList ->
            movieEntityList.map { movieEntity ->
                movieEntityConverter.fromMovieEntity(movieEntity)
            }
        }

    override fun getMovie(movieId: Int): Flow<Movie> =
        movieDao.getMovie(id = movieId).mapLatest { movieEntity ->
            movieEntityConverter.fromMovieEntity(movieEntity)
        }

    override suspend fun refreshMovies(movies: List<Movie>) {
        val movieEntityArray = movies.map { movie ->
            movieEntityConverter.toMovieEntity(movie)
        }

        movieDao.insertMovies(movieEntityArray)
    }

    override suspend fun updateMovie(movie: Movie) {
        val movieEntity = movieEntityConverter.toMovieEntity(movie)

        movieDao.updateMovie(movieEntity)
    }
}