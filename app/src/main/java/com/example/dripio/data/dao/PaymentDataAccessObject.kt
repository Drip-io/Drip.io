package com.example.dripio.data.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import com.example.dripio.data.entity.EntityComplexPayment
import com.example.dripio.data.entity.EntityPayment
import com.example.dripio.domain.entity.Category
import com.example.dripio.domain.entity.Expense
import com.example.dripio.domain.entity.PaymentMethod
import java.util.*

@Dao
interface PaymentDataAccessObject {

    @Insert
    fun add(entityPayment: EntityPayment)

    @Delete
    fun delete(entityPayment: EntityPayment)

    @Query("DELETE FROM EntityPayment WHERE  payment_id=:id")
    fun deleteById(id: Long)

    @Transaction
    @Query("SELECT * FROM EntityPayment")
    fun findAll(): List<EntityComplexPayment>

    @Transaction
    @Query("SELECT * FROM EntityPayment WHERE payment_id=:id")
    fun findById(id: Long): EntityComplexPayment?

    @Transaction
    @Query("SELECT * FROM EntityPayment WHERE paid_at >= :iDate AND paid_at <= :fDate")
    fun findAllWithPaidAtWithin(iDate: Long, fDate: Long): List<EntityComplexPayment>

//    @Update
//    fun update(entityPayment: EntityPayment)

    @Query("UPDATE EntityPayment SET name=:name, expense=:expenseId, payment_value=:paymentValue, payment_method=:paymentMethodId, category=:categoryId, paid_at=:paidDate, updated_at=:updatedAt WHERE payment_id=:id")
    fun update(id: Long, name: String, expenseId: Long?, paymentValue: Float, paymentMethodId: Long?, categoryId: Long?, paidDate: Long, updatedAt: Long)
}