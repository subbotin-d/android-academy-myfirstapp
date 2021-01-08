package ru.subbotind.android.academy.myfirstapp.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.subbotind.android.academy.myfirstapp.data.network.GenreService
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import javax.inject.Inject

interface GenreRemoteDataSource {

    suspend fun getGenres(): List<Genre>
}

class GenreRemoteDataSourceIml @Inject constructor(
    private val genreService: GenreService
) : GenreRemoteDataSource {

    override suspend fun getGenres(): List<Genre> = withContext(Dispatchers.IO) {
        genreService.getGenre().genres
    }
}