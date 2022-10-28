package com.example.dripio.domain.entity

import com.example.dripio.data.entity.EntityPaymentMethod

data class PaymentMethod(
    var id: Long,
    var name: String,
    var color: String
)

fun PaymentMethod.toEntity() = EntityPaymentMethod(
    id = null,
    name = this.name,
    color = this.color
)
