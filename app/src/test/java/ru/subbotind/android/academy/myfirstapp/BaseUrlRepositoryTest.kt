package ru.subbotind.android.academy.myfirstapp

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.subbotind.android.academy.myfirstapp.data.datasource.BaseUrlLocalDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.BaseUrlRemoteDataSource
import ru.subbotind.android.academy.myfirstapp.data.datasource.ConfigurationLastLoadingTimeDataSource
import ru.subbotind.android.academy.myfirstapp.data.repository.BaseImageUrlRepositoryImpl
import java.util.*

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class BaseUrlRepositoryTest {

    private companion object {
        const val ONE_DAY_MILLIS: Long = 24 * 60 * 60 * 100
    }

    private val remoteDataSource = mock(BaseUrlRemoteDataSource::class.java)
    private val localDataSource = mock(BaseUrlLocalDataSource::class.java)
    private val configurationLastLoadingTimeDataSource =
        mock(ConfigurationLastLoadingTimeDataSource::class.java)

    private val baseUrRepository = BaseImageUrlRepositoryImpl(
        remoteDataSource,
        localDataSource,
        configurationLastLoadingTimeDataSource
    )

    @Test
    fun `WHEN fetching last time is zero EXPECT fetch data from server`() = runBlockingTest {
        `when`(configurationLastLoadingTimeDataSource.get()).thenReturn(0)
        `when`(remoteDataSource.get()).thenReturn("http://baseurl-remote.com")

        val baseUrl = baseUrRepository.getBaseUrl()

        verify(localDataSource).put("http://baseurl-remote.com")
        verify(configurationLastLoadingTimeDataSource).put(anyLong())

        assertEquals("http://baseurl-remote.com", baseUrl)
    }

    @Test
    fun `WHEN cache is expired EXPECT fetch data from server`() = runBlockingTest {
        val yesterday = Calendar.getInstance().time.time - ONE_DAY_MILLIS - 1
        `when`(configurationLastLoadingTimeDataSource.get()).thenReturn(yesterday)
        `when`(remoteDataSource.get()).thenReturn("http://baseurl.com")

        val baseUrl = baseUrRepository.getBaseUrl()

        verify(localDataSource).put("http://baseurl.com")
        verify(configurationLastLoadingTimeDataSource).put(anyLong())

        assertEquals("http://baseurl.com", baseUrl)
    }

    @Test
    fun `WHEN cache is not expired EXPECT fetch data from local storage`() = runBlockingTest {
        val today = Calendar.getInstance().time.time
        `when`(configurationLastLoadingTimeDataSource.get()).thenReturn(today)
        `when`(localDataSource.get()).thenReturn("http://baseurl-local.com")

        val baseUrl = baseUrRepository.getBaseUrl()

        assertEquals("http://baseurl-local.com", baseUrl)
    }
}