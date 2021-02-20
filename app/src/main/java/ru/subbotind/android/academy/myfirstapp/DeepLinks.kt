package ru.subbotind.android.academy.myfirstapp

import android.net.Uri
import androidx.core.net.toUri

object DeepLinks {

    fun getMovieDetailsDeepLink(movieId: Int): Uri =
        "https://subbotind.ru/movie/$movieId".toUri()
}