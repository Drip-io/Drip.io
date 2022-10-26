package com.example.dripio.domain.entity

import java.time.Instant
import java.util.*

data class PaymentMethod(
    var id: Long,
    var name: String,
    var color: String
)