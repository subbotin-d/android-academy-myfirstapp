package ru.subbotind.android.academy.myfirstapp.data

import androidx.annotation.DrawableRes

data class Movie(
    val id: Long,
    val title: String,
    val duration: Int,
    val pgRating: String,
    val userRating: Int,
    val reviewsCount: Int,
    val isLiked: Boolean,
    val storyLine: String,
    val cast: List<Actor>,
    val tags: String,
    @DrawableRes
    val promoImage: Int,
    @DrawableRes
    val mainImageBackground: Int
)