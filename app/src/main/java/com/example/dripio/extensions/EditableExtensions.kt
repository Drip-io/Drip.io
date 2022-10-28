package com.example.dripio.extensions

import android.text.Editable

fun Editable.setText(text: String) {
    this.clear()
    this.insert(0, text)
}
