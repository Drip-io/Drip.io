package com.dripio.extensions

import java.util.*

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

fun Date.setDayMonthTo(dayMonth: Int): Date {
    val calendar = this.toCalendar()
    calendar.set(Calendar.DAY_OF_MONTH, dayMonth)
    return calendar.toDate()
}

fun Date.formatToMonthYear(): String {
    val calendar = this.toCalendar()
    val month = calendar[Calendar.MONTH] + 1
    val year = calendar[Calendar.YEAR]
    return "$month / $year"
}

fun Date.formatToDayMonthYear(): String {
    val calendar = this.toCalendar()
    val day = calendar[Calendar.DAY_OF_MONTH] + 1
    val month = calendar[Calendar.MONTH] + 1
    val year = calendar[Calendar.YEAR]
    return "$day / $month / $year"
}
