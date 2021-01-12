package ru.subbotind.android.academy.myfirstapp.presentation.error

sealed class ErrorState {
    object InternetError : ErrorState()
    class ServerError(val cause: String) : ErrorState()
    class UnexpectedError(val cause: String) : ErrorState()
}