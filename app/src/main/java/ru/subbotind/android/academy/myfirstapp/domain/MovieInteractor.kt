package ru.subbotind.android.academy.myfirstapp.domain

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import ru.subbotind.android.academy.myfirstapp.data.Movie
import ru.subbotind.android.academy.myfirstapp.data.loadMovies

interface MovieInteractor {

    suspend fun getMovies(): List<Movie>

    suspend fun getMovie(id: Int): Movie?
}

class MovieInteractorImpl(
    private val coroutineScope: CoroutineScope,
    private val context: Context
) : MovieInteractor {

    override suspend fun getMovies(): List<Movie> {
        val moviesDeferred = coroutineScope.async {
            loadMovies(context)
        }

        return moviesDeferred.await()
    }

    override suspend fun getMovie(id: Int): Movie? {
        val movieDeferred = coroutineScope.async {
            loadMovies(context).findLast { movie ->
                movie.id == id
            }
        }

        return movieDeferred.await()
    }
}