package com.example.dripio.extensions

import java.util.*

fun Date.toCalendar(): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = this
    return calendar
}

fun Date.month(): Int = this.toCalendar()[Calendar.MONTH]
fun Date.year(): Int = this.toCalendar()[Calendar.YEAR]

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