package ru.subbotind.android.academy.myfirstapp.ui.error

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.subbotind.android.academy.myfirstapp.R

class NoInternetDialogFragment : DialogFragment() {

    companion object {
        fun newInstance() = NoInternetDialogFragment()
    }

    private val buttonClickListener: OnRetryButtonClickListener
        get() = targetFragment as OnRetryButtonClickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.no_internet_title)
            .setMessage(R.string.no_internet_message)
            .setPositiveButton(R.string.retry_button) { dialog, _ ->
                buttonClickListener.onRetryButtonClick()
                dialog.dismiss()
            }
            .create()
    }
}