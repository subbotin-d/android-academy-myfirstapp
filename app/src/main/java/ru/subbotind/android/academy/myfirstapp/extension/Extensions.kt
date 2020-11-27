package ru.subbotind.android.academy.myfirstapp.extension

import android.content.Context

fun Context.calculateSpanCount(spanWidthPixels: Int): Int {
    val screenWidthPixels = this.resources.displayMetrics.widthPixels
    return (screenWidthPixels / spanWidthPixels + 0.5).toInt()
}