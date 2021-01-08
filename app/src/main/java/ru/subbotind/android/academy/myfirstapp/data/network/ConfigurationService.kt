package ru.subbotind.android.academy.myfirstapp.data.network

import retrofit2.http.GET
import ru.subbotind.android.academy.myfirstapp.data.model.ConfigurationModel

interface ConfigurationService {

    @GET("configuration")
    suspend fun getConfiguration(): ConfigurationModel
}