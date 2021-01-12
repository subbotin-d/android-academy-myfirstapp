package ru.subbotind.android.academy.myfirstapp.data.network.exception

import ru.subbotind.android.academy.myfirstapp.data.network.ErrorType
import java.io.IOException

class ServerErrorException(val type: ErrorType, val reason: String) : IOException(reason)