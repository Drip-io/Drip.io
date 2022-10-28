package com.example.dripio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dripio.data.dao.PaymentDataAccessObject
import com.example.dripio.data.dao.PaymentMethodsDataAccessObject
import com.example.dripio.data.entity.EntityCategory
import com.example.dripio.data.entity.EntityExpense
import com.example.dripio.data.entity.EntityPayment
import com.example.dripio.data.entity.EntityPaymentMethod

@Database(entities = [EntityPaymentMethod::class, EntityPayment::class, EntityCategory::class, EntityExpense::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun paymentsDataAccessObject(): PaymentDataAccessObject
    abstract fun paymentMethodsDataAccessObject(): PaymentMethodsDataAccessObject

    companion object {
        fun create(context: Context): AppDatabase = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "dripio-database"
        ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }
}
