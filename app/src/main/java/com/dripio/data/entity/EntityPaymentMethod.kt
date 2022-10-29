package com.dripio.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dripio.domain.entity.PaymentMethod

@Entity
data class EntityPaymentMethod(
    @PrimaryKey
    @ColumnInfo(name = "payment_method_id")
    var id: Long? = null,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "color") var color: String
)

fun EntityPaymentMethod.toDomain() = PaymentMethod(
    id = this.id ?: 0,
    name = this.name,
    color = this.color
)
