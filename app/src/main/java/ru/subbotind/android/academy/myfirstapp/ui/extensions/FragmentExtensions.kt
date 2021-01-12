package ru.subbotind.android.academy.myfirstapp.ui.extensions

import androidx.fragment.app.Fragment
import ru.subbotind.android.academy.myfirstapp.ui.error.ErrorRetryDialogFragment
import ru.subbotind.android.academy.myfirstapp.ui.error.NoInternetDialogFragment

fun Fragment.showInternetErrorDialog() {
    val defaultDialogRequestCode = 0
    val noInternetDialogFragment = NoInternetDialogFragment.newInstance()
    parentFragmentManager.let {
        noInternetDialogFragment.showNow(
            it,
            NoInternetDialogFragment::class.java.simpleName
        )
    }

    noInternetDialogFragment.setTargetFragment(this, defaultDialogRequestCode)
}

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