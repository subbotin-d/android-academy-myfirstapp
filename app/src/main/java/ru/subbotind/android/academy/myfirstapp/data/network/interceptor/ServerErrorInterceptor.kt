package ru.subbotind.android.academy.myfirstapp.data.network.interceptor

import android.content.Context
import okhttp3.Interceptor
import okhttp3.Response
import ru.subbotind.android.academy.myfirstapp.R
import ru.subbotind.android.academy.myfirstapp.data.network.ErrorType
import ru.subbotind.android.academy.myfirstapp.data.network.exception.ServerErrorException
import ru.subbotind.android.academy.myfirstapp.data.network.exception.UnexpectedServerErrorException
import java.net.HttpURLConnection.*

class ServerErrorInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val response = chain.proceed(originalRequest)

        when (response.code) {
            HTTP_OK -> return response
            HTTP_INTERNAL_ERROR -> throw createInternalError()
            HTTP_UNAVAILABLE -> throw createUnavailableError()
            HTTP_BAD_REQUEST -> throw createBadRequestError()
            HTTP_NOT_FOUND -> throw createNotFoundError()
            else -> throw createUnexpectedError()
        }
    }

    private fun createUnexpectedError(message: String? = null): UnexpectedServerErrorException =
        UnexpectedServerErrorException(message ?: context.getString(R.string.unexpected_error_text))

    private fun createNotFoundError(): ServerErrorException = ServerErrorException(
        ErrorType.NOT_FOUND_ERROR,
        context.getString(R.string.not_found_error_text)
    )

    private fun createBadRequestError(): ServerErrorException =
        ServerErrorException(
            ErrorType.BAD_PARAMS_ERROR,
            context.getString(R.string.bad_request_error_text)
        )

    private fun createUnavailableError(): ServerErrorException =
        ServerErrorException(
            ErrorType.SERVER_UNAVAILABLE_ERROR,
            context.getString(R.string.server_unavailable_error_text)
        )

    private fun createInternalError(): ServerErrorException =
        ServerErrorException(
            ErrorType.INTERNAL_ERROR,
            context.getString(R.string.server_internal_error_text)
        )
}