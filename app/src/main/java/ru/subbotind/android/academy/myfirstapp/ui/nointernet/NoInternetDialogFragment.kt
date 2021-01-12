package ru.subbotind.android.academy.myfirstapp.ui.nointernet

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import ru.subbotind.android.academy.myfirstapp.R

class NoInternetDialogFragment : DialogFragment() {

//    private var buttonClickListener: OnDialogButtonClickListener? = null

    companion object {
        fun newInstance() = NoInternetDialogFragment()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
//        buttonClickListener = targetFragment as? OnDialogButtonClickListener
        return AlertDialog.Builder(requireActivity())
            .setTitle(R.string.no_internet_title)
            .setMessage(R.string.no_internet_message)
            .setPositiveButton(R.string.no_internet_button) { dialog, _ ->
//                buttonClickListener?.onDialogButtonClick()
                dialog.dismiss()
                dialog.cancel()
            }
            .create()
    }

    interface OnDialogButtonClickListener {
        fun onDialogButtonClick()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        dialog?.dismiss()
        dialog?.cancel()
//        buttonClickListener = null
    }
}