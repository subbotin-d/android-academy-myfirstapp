package ru.subbotind.android.academy.myfirstapp.ui.extensions

import androidx.fragment.app.Fragment
import ru.subbotind.android.academy.myfirstapp.ui.error.ErrorRetryDialogFragment

fun Fragment.showRetryErrorDialog(message: String) {
    val defaultDialogRequestCode = 0
    val errorDialogFragment = ErrorRetryDialogFragment.newInstance(message)

    parentFragmentManager.let {
        errorDialogFragment.showNow(
            it,
            ErrorRetryDialogFragment::class.java.simpleName
        )
    }

    errorDialogFragment.setTargetFragment(this, defaultDialogRequestCode)
}