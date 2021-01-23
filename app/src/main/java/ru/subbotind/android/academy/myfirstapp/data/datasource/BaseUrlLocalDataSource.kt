package ru.subbotind.android.academy.myfirstapp.data.datasource

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface BaseUrlLocalDataSource {

    suspend fun get(): String
    suspend fun put(baseImageUrl: String)
}

class BaseUrlLocalDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : BaseUrlLocalDataSource {

    private companion object {
        const val BASE_IMAGE_URL_KEY = "BASE_IMAGE_URL_KEY"
    }

    override suspend fun get(): String = withContext(Dispatchers.IO) {
        val baseImageUrl = sharedPreferences.getString(BASE_IMAGE_URL_KEY, null)

        baseImageUrl ?: throw IllegalStateException("There is no cached value")
    }


    override suspend fun put(baseImageUrl: String) = withContext(Dispatchers.IO) {
        sharedPreferences.edit()
            .putString(BASE_IMAGE_URL_KEY, baseImageUrl)
            .apply()
    }
}