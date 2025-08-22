package com.example.inventorydashboard.presentation.extentions

import android.view.View
import android.widget.AdapterView
import android.widget.Spinner

inline fun Spinner.onItemSelected(
    crossinline block: (position: Int, selected: String) -> Unit
) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>, view: View?, position: Int, id: Long
        ) = block(position, parent.getItemAtPosition(position)?.toString().orEmpty())

        override fun onNothingSelected(parent: AdapterView<*>) = Unit
    }
}
