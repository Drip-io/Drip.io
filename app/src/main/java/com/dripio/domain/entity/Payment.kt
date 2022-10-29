package com.dripio.domain.entity

import com.dripio.data.entity.EntityPayment
import java.util.*

data class Payment(
    var id: Long,
    var name: String,
    var paymentValue: Float,
    var expense: Expense?,
    var paymentMethod: PaymentMethod?,
    var category: Category?,
    var paidAt: Date,
    var createdAt: Date,
    var updatedAt: Date
)

fun Payment.toEntity() = EntityPayment(
    id = null,
    name = this.name,
    paymentValue = this.paymentValue,
    expenseId = this.expense?.id,
    paymentMethodId = this.paymentMethod?.id,
    categoryId = this.category?.id,
    updatedAt = this.updatedAt.time,
    createdAt = this.createdAt.time,
    paidAt = this.paidAt.time
)
