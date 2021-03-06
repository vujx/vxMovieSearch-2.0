package com.algebra.moviefinder30.presentation.ui.dialog

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.algebra.moviefinder30.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CustomDialog(private val title: String, private val listener: DialogListener) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): AlertDialog {
        return MaterialAlertDialogBuilder(requireActivity(), R.style.MaterialAlertDialog_OK_color).setView(view)
            .setMessage(title)
            .setPositiveButton("Ok") { _, _ ->
                listener.okPress()
            }.setNegativeButton("Cancel") { _, _ ->
                getDialog()?.cancel()
            }.create()
    }
}
