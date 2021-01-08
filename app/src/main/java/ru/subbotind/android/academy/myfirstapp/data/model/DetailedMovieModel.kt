package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre

@Serializable
data class DetailedMovieModel(
    val id: Int,
    val title: String,
    val overview: String? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null,
    @SerialName("poster_path")
    val posterPath: String? = null,
    val adult: Boolean,
    val genres: List<Genre>,
    val popularity: Float,
    val runtime: Int? = null,
    val tagline: String? = null,
    @SerialName("vote_average")
    val voteAverage: Float,
    @SerialName("vote_count")
    val voteCount: Int
)