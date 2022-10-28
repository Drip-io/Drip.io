package com.example.dripio.domain.repository.api

import com.example.dripio.domain.entity.Payment
import java.util.*

interface PaymentRepository : IRepository<Payment, Long> {
    suspend fun add(name: String?, expenseId: Long?, paymentValue: Float, paymentMethodId: Long?, categoryId: Long?, paidDate: Date)
    suspend fun findAllWithinDates(initialDate: Date, finalDate: Date): List<Payment>
}
