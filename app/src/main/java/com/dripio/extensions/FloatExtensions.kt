package com.dripio.extensions

fun Float.toMoneyStringWithComma() = this.toMoneyStringWithPeriod().replace(".", ",")
fun Float.toMoneyStringWithPeriod() = String.format("%.2f", this).replace(",", ".")
