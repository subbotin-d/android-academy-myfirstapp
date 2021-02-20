package ru.subbotind.android.academy.myfirstapp.background

import android.content.Context
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WorkController @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun startWork(jobRequest: WorkRequest) {
        WorkManager.getInstance(context)
            .enqueue(jobRequest)
    }
}