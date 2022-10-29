package com.dripio.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dripio.data.entity.EntityPaymentMethod

@Dao
interface PaymentMethodsDataAccessObject {

    @Insert
    fun add(entity: EntityPaymentMethod)

    @Delete
    fun delete(entity: EntityPaymentMethod)

    @Query("SELECT * FROM EntityPaymentMethod")
    fun findAll(): List<EntityPaymentMethod>

    @Query("SELECT * FROM EntityPaymentMethod WHERE payment_method_id=:id")
    fun findById(id: Long): EntityPaymentMethod?
}
