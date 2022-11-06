package com.dripio.data.entity

import androidx.room.Embedded
import androidx.room.Relation
import com.dripio.domain.entity.Expense

data class EntityExpenseWithPayments(
    @Embedded val expense: EntityExpense,
    @Relation(
        parentColumn = "expense_id",
        entityColumn = "expense"
    )
    val relatedPayments: List<EntityPayment>,
)

fun EntityExpenseWithPayments.toDomain() = Expense(
    id = this.expense.id ?: 0,
    name = this.expense.name,
    installments = this.expense.installments,
    totalValue = this.expense.totalValue,
    relatedPayments = this.relatedPayments.map { it.toSimpleDomain() }
)