package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class MovieResultModel(val results: List<MovieModel>)