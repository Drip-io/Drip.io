package com.dripio.domain.repository.api

import com.dripio.domain.entity.Expense

interface ExpenseRepository : IRepository<Expense, Long> {
    fun add(name: String, installments: Int?, totalValue: Float)
}
