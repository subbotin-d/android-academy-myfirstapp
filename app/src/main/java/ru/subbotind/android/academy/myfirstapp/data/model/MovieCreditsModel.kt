package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor

@Serializable
data class MovieCreditsModel(
    @SerialName("id")
    val id: Long,
    @SerialName("cast")
    val cast: List<Actor>
)