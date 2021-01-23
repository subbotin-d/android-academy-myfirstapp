package ru.subbotind.android.academy.myfirstapp.domain.entity

data class Movie(
    val id: Int,
    val title: String,
    val overview: String? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val ratings: Float,
    val numberOfRatings: Int,
    val minimumAge: Int,
    val runtime: Int? = null,
    val genres: List<Genre>,
    val actors: List<Actor> = emptyList()
)