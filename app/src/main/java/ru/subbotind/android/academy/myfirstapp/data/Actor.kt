package ru.subbotind.android.academy.myfirstapp.data

import androidx.annotation.DrawableRes

data class Actor(
    val id: Int,
    val fullName: String,
    @DrawableRes
    val avatar: Int? = null
)