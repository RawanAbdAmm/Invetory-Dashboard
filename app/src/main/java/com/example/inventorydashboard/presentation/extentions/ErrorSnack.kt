package com.example.inventorydashboard.presentation.extentions

import com.google.android.material.snackbar.Snackbar
import android.view.View
import com.example.inventorydashboard.R

fun View.showErrorSnack(
    message: String,
    duration: Int = Snackbar.LENGTH_INDEFINITE,
    retryText: CharSequence = context.getString(R.string.retry),
    onRetry: (() -> Unit)? = null
) {
    val snack = Snackbar.make(this, message, duration)
    if (onRetry != null) {
        snack.setAction(retryText) { onRetry() }
    }
    snack.show()
}