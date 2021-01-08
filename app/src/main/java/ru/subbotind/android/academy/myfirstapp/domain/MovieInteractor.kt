package ru.subbotind.android.academy.myfirstapp.domain

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.data.loadMovies
import javax.inject.Inject

interface MovieInteractor {

    suspend fun getMovies(): List<Movie>

    suspend fun getMovie(id: Int): Movie?
}

class MovieInteractorImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : MovieInteractor {

    override suspend fun getMovies(): List<Movie> = withContext(Dispatchers.IO) {
        loadMovies(context)
    }

    override suspend fun getMovie(id: Int): Movie? = withContext(Dispatchers.IO) {
        loadMovies(context).findLast { movie ->
            movie.id == id
        }
    }
}