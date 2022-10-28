package com.example.dripio.domain.repository.impl

import com.example.dripio.data.AppDatabase
import com.example.dripio.data.entity.EntityPaymentMethod
import com.example.dripio.data.entity.toDomain
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.domain.entity.toEntity
import com.example.dripio.domain.repository.api.PaymentMethodsRepository

class PaymentMethodsRepositoryImpl(private val appDatabase: AppDatabase) : PaymentMethodsRepository {

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