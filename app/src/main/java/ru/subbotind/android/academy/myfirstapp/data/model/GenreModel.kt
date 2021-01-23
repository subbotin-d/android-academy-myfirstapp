package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.subbotind.android.academy.myfirstapp.domain.entity.Genre

@Serializable
data class GenreModel(
    @SerialName("genres")
    val genres: List<Genre>
)