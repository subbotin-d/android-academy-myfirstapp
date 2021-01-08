package ru.subbotind.android.academy.myfirstapp.data.network

import retrofit2.http.GET
import ru.subbotind.android.academy.myfirstapp.data.model.GenreModel

interface GenreService {

    @GET("genre/movie/list")
    suspend fun getGenre(): GenreModel
}