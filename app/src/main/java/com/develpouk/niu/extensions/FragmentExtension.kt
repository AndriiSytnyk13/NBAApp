package com.develpouk.niu.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.errorSnackBar(message: String, action: () -> Unit ) {
    Snackbar.make(requireContext(), requireView(), message, Snackbar.LENGTH_INDEFINITE)
        .setAction("Retry") {
            action()
        }.show()
}