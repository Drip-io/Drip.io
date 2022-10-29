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
    @ColumnInfo(name = "name") var name: String
)

fun EntityExpense.toDomain() = Expense(
    id = this.id ?: 0
)
