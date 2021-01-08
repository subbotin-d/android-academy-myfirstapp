package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.Serializable
import ru.subbotind.android.academy.myfirstapp.domain.entity.Actor

@Serializable
data class MovieCreditsModel(
    val id: Long,
    val cast: List<Actor>
)