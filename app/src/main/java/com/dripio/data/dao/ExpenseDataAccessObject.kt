package com.dripio.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dripio.data.entity.EntityExpense
import com.dripio.data.entity.EntityPayment

@Dao
interface ExpenseDataAccessObject {

    @Insert
    fun add(expense: EntityExpense)

    @Delete
    fun delete(expense: EntityExpense)

    @Query("SELECT * FROM EntityExpense")
    fun findAll(): List<EntityExpense>

    @Query("SELECT * FROM EntityExpense where expense_id=:id")
    fun findById(id: Long): EntityExpense?
}