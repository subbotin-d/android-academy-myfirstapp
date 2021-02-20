package ru.subbotind.android.academy.myfirstapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie

interface MovieRepository {

    fun getMovies(): Flow<List<Movie>>

    fun getNewMovies(): Flow<List<Movie>>

    fun getMovie(movieId: Int): Flow<Movie>

    suspend fun fetchMovies()

    suspend fun fetchMovie(movieId: Int)
}