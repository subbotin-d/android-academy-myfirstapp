package ru.subbotind.android.academy.myfirstapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import ru.subbotind.android.academy.myfirstapp.data.datasource.*
import ru.subbotind.android.academy.myfirstapp.data.network.ConfigurationService
import ru.subbotind.android.academy.myfirstapp.data.repository.BaseImageUrlRepository
import ru.subbotind.android.academy.myfirstapp.data.repository.BaseImageUrlRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface ConfigurationModule {

    companion object {
        @Provides
        @Singleton
        fun provideConfigurationService(retrofit: Retrofit): ConfigurationService =
            retrofit.create()
    }

    @Binds
    @Singleton
    fun bindsBaseUrlRemoteDataSource(baseUrlRemoteDataSource: BaseUrlRemoteDataSourceImpl): BaseUrlRemoteDataSource

    @Binds
    @Singleton
    fun bindsBaseUrlLocalDataSource(baseUrlLocalDataSource: BaseUrlLocalDataSourceImpl): BaseUrlLocalDataSource

    @Binds
    @Singleton
    fun bindsConfigurationLastLoadingTimeDataSource(configurationLastLoadingTimeDataSource: ConfigurationLastLoadingTimeDataSourceImpl): ConfigurationLastLoadingTimeDataSource

    @Binds
    @Singleton
    fun bindsBaseUrlRepository(baseImageUrlRepository: BaseImageUrlRepositoryImpl): BaseImageUrlRepository
}