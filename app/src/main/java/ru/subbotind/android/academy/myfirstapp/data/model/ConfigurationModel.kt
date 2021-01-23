package ru.subbotind.android.academy.myfirstapp.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ConfigurationModel(
    @SerialName("images")
    val images: ImageModel
)