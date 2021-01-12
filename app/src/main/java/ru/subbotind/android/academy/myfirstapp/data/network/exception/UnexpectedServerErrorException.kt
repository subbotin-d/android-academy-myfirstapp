package ru.subbotind.android.academy.myfirstapp.data.network.exception

import java.io.IOException

class UnexpectedServerErrorException(reason: String) : IOException(reason)