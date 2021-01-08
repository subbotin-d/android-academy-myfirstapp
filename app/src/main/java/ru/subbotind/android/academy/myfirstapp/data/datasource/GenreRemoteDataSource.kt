package ru.subbotind.android.academy.myfirstapp.data.datasource

import ru.subbotind.android.academy.myfirstapp.data.network.GenreService
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre
import javax.inject.Inject

interface GenreRemoteDataSource {

    suspend fun getGenres(): List<Genre>
}

class GenreRemoteDataSourceIml @Inject constructor(
    private val genreService: GenreService
) : GenreRemoteDataSource {

    override suspend fun getGenres(): List<Genre> = genreService.getGenre().genres
}