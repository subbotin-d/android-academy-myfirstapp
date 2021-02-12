package ru.subbotind.android.academy.myfirstapp.data.repository

import android.util.Log
import ru.subbotind.android.academy.myfirstapp.data.datasource.BaseUrlLocalDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.BaseUrlRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.ConfigurationLastLoadingTimeDataSource
import java.util.*
import javax.inject.Inject

interface BaseImageUrlRepository {

    suspend fun getBaseUrl(): String
}

class BaseImageUrlRepositoryImpl @Inject constructor(
    private val remoteDataSource: BaseUrlRemoteDataSource,
    private val localDataSource: BaseUrlLocalDataSource,
    private val configurationLastLoadingTimeDataSource: ConfigurationLastLoadingTimeDataSource
) : BaseImageUrlRepository {

    private companion object {
        const val ONE_DAY_MILLIS: Long = 24 * 60 * 60 * 100
    }

    override suspend fun getBaseUrl(): String {
        val lastLoadingTimeDataSource: Long = configurationLastLoadingTimeDataSource.get()
        val currentTimeMillis: Long = Calendar.getInstance().time.time

        val configuration: String

        if (lastLoadingTimeDataSource == 0L || currentTimeMillis - lastLoadingTimeDataSource > ONE_DAY_MILLIS) {
            configuration = remoteDataSource.get()
            localDataSource.put(configuration)

            saveLastLoadingTime()
        } else {
            configuration = localDataSource.get()
        }

        Log.d("BASE_URL", configuration)
        return configuration
    }

    private suspend fun saveLastLoadingTime() {
        val currentTime = Calendar.getInstance().time.time
        configurationLastLoadingTimeDataSource.put(currentTime)
    }
}