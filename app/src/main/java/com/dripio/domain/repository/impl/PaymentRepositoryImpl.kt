package com.dripio.domain.repository.impl

import com.dripio.data.AppDatabase
import com.dripio.data.entity.EntityPayment
import com.dripio.data.entity.toDomain
import com.dripio.domain.entity.Payment
import com.dripio.domain.entity.toEntity
import com.dripio.domain.repository.api.PaymentRepository
import java.util.*

class PaymentRepositoryImpl(private val appDatabase: AppDatabase) : PaymentRepository {

    override suspend fun find(id: Long): Payment? = appDatabase.paymentsDataAccessObject().findById(id)?.toDomain()

    override suspend fun findAll(): List<Payment> = appDatabase.paymentsDataAccessObject().findAll().toDomain()

    override suspend fun add(
        name: String?,
        expenseId: Long?,
        paymentValue: Float,
        paymentMethodId: Long?,
        categoryId: Long?,
        paidDate: Date
    ) {
        appDatabase.paymentsDataAccessObject().add(
            EntityPayment(
                id = null,
                name = name,
                paymentValue = paymentValue,
                expenseId = expenseId,
                paymentMethodId = paymentMethodId,
                categoryId = categoryId,
                updatedAt = System.currentTimeMillis(),
                createdAt = System.currentTimeMillis(),
                paidAt = paidDate.time
            )
        )
    }

    override suspend fun add(domain: Payment) {
        appDatabase.paymentsDataAccessObject().add(domain.toEntity())
    }

    override suspend fun findAllWithinDates(initialDate: Date, finalDate: Date): List<Payment> {
        return appDatabase.paymentsDataAccessObject().findAllWithPaidAtWithin(initialDate.time, finalDate.time).toDomain()
    }

    override suspend fun deleteById(id: Long) {
        appDatabase.paymentsDataAccessObject().deleteById(id)
    }

    override suspend fun update(domain: Payment) {
        appDatabase.paymentsDataAccessObject().update(
            id = domain.id,
            name = domain.name,
            expenseId = domain.expense?.id,
            paymentMethodId = domain.paymentMethod?.id,
            categoryId = domain.category?.id,
            paidDate = domain.paidAt.time,
            paymentValue = domain.paymentValue,
            updatedAt = domain.updatedAt.time
        )
    }
}
