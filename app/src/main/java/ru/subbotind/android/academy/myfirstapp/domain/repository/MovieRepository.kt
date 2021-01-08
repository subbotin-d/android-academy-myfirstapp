package ru.subbotind.android.academy.myfirstapp.domain.repository

import ru.subbotind.android.academy.myfirstapp.domain.entity.Movie

interface MovieRepository {

    suspend fun getMovies(): List<Movie>

    suspend fun getMovie(movieId: Int): Movie
}