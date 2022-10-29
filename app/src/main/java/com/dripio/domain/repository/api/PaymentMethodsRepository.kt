package com.dripio.domain.repository.api

import com.dripio.domain.entity.PaymentMethod

interface PaymentMethodsRepository : IRepository<PaymentMethod, Long> {
    suspend fun add(name: String, color: String)
}
