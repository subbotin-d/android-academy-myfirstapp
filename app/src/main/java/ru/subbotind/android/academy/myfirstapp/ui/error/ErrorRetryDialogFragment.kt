package ru.subbotind.android.academy.myfirstapp.ui.error

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import ru.subbotind.android.academy.myfirstapp.R

private const val ERROR_MESSAGE = "error_message"

class ErrorRetryDialogFragment : DialogFragment() {

    companion object {
        fun newInstance(errorMessage: String) =
            ErrorRetryDialogFragment().apply {
                arguments = bundleOf(ERROR_MESSAGE to errorMessage)
            }
    }

    private var errorMessage: String? = null

    private val retryButtonClickListener: OnRetryButtonClickListener
        get() = targetFragment as OnRetryButtonClickListener

    private val cancelButtonClickListener: OnCancelButtonClickListener
        get() = targetFragment as OnCancelButtonClickListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        errorMessage = arguments?.getString(ERROR_MESSAGE)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.error_dialog_title)
            .setMessage(errorMessage ?: "")
            .setPositiveButton(R.string.retry_button) { dialog, _ ->
                retryButtonClickListener.onRetryButtonClick()
                dialog.dismiss()
            }
            .setNegativeButton(R.string.cancel_button) { dialog, _ ->
                cancelButtonClickListener.onCancelButtonClick()
                dialog.dismiss()
            }
            .create()
    }
}