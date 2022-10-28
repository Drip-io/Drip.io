package com.example.dripio.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.example.dripio.domain.entity.Payment
import com.example.dripio.extensions.toDate

data class EntityComplexPayment(
    @Embedded val payment: EntityPayment,
    @Relation(
        parentColumn = "payment_method",
        entityColumn = "payment_method_id"
    )
    val paymentMethod: EntityPaymentMethod?,
    @Relation(
        parentColumn = "expense",
        entityColumn = "expense_id"
    )
    val expense: EntityExpense?,
    @Relation(
        parentColumn = "category",
        entityColumn = "category_id"
    )
    val category: EntityCategory?
)

fun EntityComplexPayment.toDomain() = Payment(
    id = this.payment.id ?: 0,
    name = this.payment.name ?: (this.expense?.name ?: ""),
    paymentMethod = this.paymentMethod?.toDomain(),
    expense = this.expense?.toDomain(),
    category = this.category?.toDomain(),
    updatedAt = this.payment.updatedAt.toDate(),
    createdAt = this.payment.createdAt.toDate(),
    paymentValue = this.payment.paymentValue,
    paidAt = this.payment.paidAt.toDate()
)

fun List<EntityComplexPayment>.toDomain() = this.map { it.toDomain() }
