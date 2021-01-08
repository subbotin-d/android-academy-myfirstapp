package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.Serializable
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre

@Serializable
data class GenreModel(val genres: List<Genre>)