package com.example.dripio.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import com.example.dripio.domain.entity.Expense
import com.example.dripio.domain.entity.Payment
import com.example.dripio.extensions.toDate

@Entity(foreignKeys = [
    ForeignKey(entity = EntityCategory::class, childColumns = ["category"], parentColumns = ["category_id"], onDelete = CASCADE),
    ForeignKey(entity = EntityPaymentMethod::class, childColumns = ["payment_method"], parentColumns = ["payment_method_id"], onDelete = CASCADE),
])
data class EntityPayment(
    @PrimaryKey @ColumnInfo(name = "payment_id") var id: Long? = null,
    @ColumnInfo(name = "name") var name: String?,
    @ColumnInfo(name = "payment_value") var paymentValue: Float,
    @ColumnInfo(name = "expense") var expenseId: Long?,
    @ColumnInfo(name = "payment_method") var paymentMethodId: Long?,
    @ColumnInfo(name = "category") var categoryId: Long?,
    @ColumnInfo(name = "created_at") var createdAt: Long,
    @ColumnInfo(name = "paid_at") var paidAt: Long,
    @ColumnInfo(name = "updated_at") var updatedAt: Long,
)

fun EntityPayment.toDomain(entityExpense: EntityExpense?, entityPaymentMethod: EntityPaymentMethod?, entityCategory: EntityCategory?) = Payment(
    id = this.id ?: 0,
    name = this.name ?: "",
    paymentValue = this.paymentValue,
    expense = entityExpense?.toDomain(),
    paymentMethod = entityPaymentMethod?.toDomain(),
    category = entityCategory?.toDomain(),
    updatedAt = this.updatedAt.toDate(),
    createdAt = this.createdAt.toDate(),
    paidAt = this.paidAt.toDate()
)