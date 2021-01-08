package ru.subbotind.android.academy.myfirstapp.data.datasource

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.subbotind.android.academy.myfirstapp.data.model.ConfigurationModel
import ru.subbotind.android.academy.myfirstapp.data.network.ConfigurationService
import javax.inject.Inject

interface BaseUrlRemoteDataSource {

    suspend fun get(): String
}

class BaseUrlRemoteDataSourceImpl @Inject constructor(
    private val configurationService: ConfigurationService
) : BaseUrlRemoteDataSource {

    override suspend fun get(): String = withContext(Dispatchers.IO) {
        val configurationModel: ConfigurationModel = configurationService.getConfiguration()
        val baseUrl =
            "${configurationModel.images.secureBaseURL}${configurationModel.images.backdropSizes.last()}"

        baseUrl
    }
}