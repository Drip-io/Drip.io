package com.dripio.domain.repository.impl

import com.dripio.data.AppDatabase
import com.dripio.data.entity.EntityPaymentMethod
import com.dripio.data.entity.toDomain
import com.dripio.domain.entity.PaymentMethod
import com.dripio.domain.entity.toEntity
import com.dripio.domain.repository.api.PaymentMethodsRepository

class PaymentMethodsRepositoryImpl(private val appDatabase: AppDatabase) :
    PaymentMethodsRepository {

    override suspend fun find(id: Long): PaymentMethod? = appDatabase.paymentMethodsDataAccessObject().findById(id)?.toDomain()

    override suspend fun findAll(): List<PaymentMethod> = appDatabase.paymentMethodsDataAccessObject().findAll().map { it.toDomain() }

    override suspend fun add(name: String, color: String) {
        appDatabase.paymentMethodsDataAccessObject().add(
            EntityPaymentMethod(
                id = null,
                name = name,
                color = color
            )
        )
    }

    override suspend fun add(domain: PaymentMethod) {
        appDatabase.paymentMethodsDataAccessObject().add(domain.toEntity())
    }

    override suspend fun deleteById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun update(domain: PaymentMethod) {
        TODO("Not yet implemented")
    }
}
