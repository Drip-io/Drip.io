package com.example.dripio.extensions

import java.lang.Exception

fun String?.toIntOrZero(): Int =
    try {
        this?.toInt() ?: 0
    } catch(ex: Exception) {
        0
    }
