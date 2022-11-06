package com.dripio.domain.entity

import com.dripio.data.entity.EntityExpense

data class Expense(
    val id: Long,
    val name: String,
    val installments: Int?,
    val totalValue: Float,
    val relatedPayments: List<SimplePayment> = listOf()
) {
    fun getInstallmentValue(): Float =
        if (installments == null || installments == 0 || installments == 1) {
            totalValue
        } else {
            totalValue / installments.toFloat()
        }
}

fun Expense.toEntity(): EntityExpense = EntityExpense(
    id = this.id,
    name = this.name,
    installments = this.installments,
    totalValue = this.totalValue
)
