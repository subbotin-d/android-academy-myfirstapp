package ru.subbotind.android.academy.myfirstapp.data.datasource

import android.content.SharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ConfigurationLastLoadingTimeDataSource {

    suspend fun get(): Long
    suspend fun put(loadingLastTime: Long)
}

class ConfigurationLastLoadingTimeDataSourceImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ConfigurationLastLoadingTimeDataSource {

    private companion object {
        const val LOADING_LAST_TIME_KEY = "LOADING_LAST_TIME_KEY"
        const val DEFAULT_TIME_VALUE = 0L
    }

    override suspend fun get(): Long = withContext(Dispatchers.IO) {
        val loadingLastTime = sharedPreferences.getLong(LOADING_LAST_TIME_KEY, DEFAULT_TIME_VALUE)

        loadingLastTime
    }


    override suspend fun put(loadingLastTime: Long) = withContext(Dispatchers.IO) {
        sharedPreferences.edit()
            .putLong(LOADING_LAST_TIME_KEY, loadingLastTime)
            .apply()
    }
}