package ru.subbotind.android.academy.myfirstapp.data.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import ru.subbotind.android.academy.myfirstapp.BuildConfig

class MovieApiHeaderInterceptor : Interceptor {

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url

        val request = originalRequest.newBuilder()
            .url(originalHttpUrl)
            .addHeader(AUTHORIZATION_HEADER, BuildConfig.API_KEY)
            .build()

        return chain.proceed(request)
    }
}