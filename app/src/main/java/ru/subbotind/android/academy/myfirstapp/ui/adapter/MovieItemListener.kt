package ru.subbotind.android.academy.myfirstapp.ui.adapter

import ru.subbotind.android.academy.myfirstapp.data.Movie

class MovieItemListener(
    val likeIconClickListener: () -> Unit,
    val cardClickListener: (Long) -> Unit
) {
    fun onLikeClick() = likeIconClickListener()
    fun onMovieItemClick(movie: Movie) = cardClickListener(movie.id)
}