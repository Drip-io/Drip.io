package com.example.dripio.domain.repository.api

import com.example.dripio.domain.entity.PaymentMethod

interface PaymentMethodsRepository : IRepository<PaymentMethod, Long> {
    suspend fun add(name: String, color: String)
}