package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre

@Serializable
data class DetailedMovieModel(
    @SerialName("id")
    val id: Int,
    @SerialName("title")
    val title: String,
    @SerialName("overview")
    val overview: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    @SerialName("adult")
    val adult: Boolean,
    @SerialName("genres")
    val genres: List<Genre>,
    @SerialName("popularity")
    val popularity: Float,
    @SerialName("runtime")
    val runtime: Int? = null,
    @SerialName("tagline")
    val tagline: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int
)