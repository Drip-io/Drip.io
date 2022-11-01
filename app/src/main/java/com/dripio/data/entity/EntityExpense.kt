package com.dripio.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dripio.domain.entity.Expense

@Entity
data class EntityExpense(
    @PrimaryKey
    @ColumnInfo(name = "expense_id")
    var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "installments") val installments: Int? = null,
    @ColumnInfo(name = "total_value") val totalValue: Float,
)

fun EntityExpense.toDomain() = Expense(
    id = this.id ?: 0,
    name = this.name,
    installments = this.installments,
    totalValue = this.totalValue
)
