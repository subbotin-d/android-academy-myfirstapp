package ru.subbotind.android.academy.myfirstapp.data.network

import retrofit2.http.GET
import retrofit2.http.Path
import ru.subbotind.android.academy.myfirstapp.data.model.DetailedMovieModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieCreditsModel
import ru.subbotind.android.academy.myfirstapp.data.model.MovieResultModel

interface MovieService {

    @GET("movie/upcoming")
    suspend fun getMovies(): MovieResultModel

    @GET("movie/{movie_id}")
    suspend fun getMovie(@Path("movie_id") movieId: Int): DetailedMovieModel

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): MovieCreditsModel
}