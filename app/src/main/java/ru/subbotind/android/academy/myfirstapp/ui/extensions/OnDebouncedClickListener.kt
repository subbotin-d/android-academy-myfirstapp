package ru.subbotind.android.academy.myfirstapp.ui.extensions

import android.view.View
import ru.subbotind.android.academy.myfirstapp.ui.extensions.OnDebouncedClickListener.Companion.DEFAULT_DEBOUNCE_INTERVAL
import java.lang.System.currentTimeMillis

abstract class OnDebouncedClickListener(
    private val delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL
) : View.OnClickListener {

    companion object {
        const val DEFAULT_DEBOUNCE_INTERVAL = 500L
    }

    private var lastClickTimestamp: Long = -1L

    override fun onClick(view: View) {
        val now = currentTimeMillis()
        if (lastClickTimestamp == -1L || now >= (lastClickTimestamp + delayBetweenClicks)) {
            onDebouncedClick(view)
            lastClickTimestamp = now
        }
    }

    abstract fun onDebouncedClick(view: View)
}

fun View.setOnDebouncedClickListener(
    delayBetweenClicks: Long = DEFAULT_DEBOUNCE_INTERVAL,
    click: (view: View) -> Unit
) {
    setOnClickListener(object : OnDebouncedClickListener(delayBetweenClicks) {
        override fun onDebouncedClick(view: View) {
            click.invoke(view)
        }
    })
}
