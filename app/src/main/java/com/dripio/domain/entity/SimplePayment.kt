package com.dripio.domain.entity

import java.util.*

data class SimplePayment(
    var id: Long,
    var name: String,
    var paymentValue: Float,
    var paidAt: Date,
    var createdAt: Date,
    var updatedAt: Date
)