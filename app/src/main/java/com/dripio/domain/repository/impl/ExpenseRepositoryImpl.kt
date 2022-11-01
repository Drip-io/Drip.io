package com.dripio.domain.repository.impl

import com.dripio.data.AppDatabase
import com.dripio.data.entity.EntityExpense
import com.dripio.data.entity.toDomain
import com.dripio.domain.entity.Expense
import com.dripio.domain.entity.toEntity
import com.dripio.domain.repository.api.ExpenseRepository

class ExpenseRepositoryImpl(private val appDatabase: AppDatabase) : ExpenseRepository {

    override suspend fun find(id: Long): Expense? =
        appDatabase.expenseDataAccessObject().findById(id)?.toDomain()

    override suspend fun findAll(): List<Expense> =
        appDatabase.expenseDataAccessObject().findAll().map { it.toDomain() }

    override fun add(name: String, installments: Int?, totalValue: Float) {
        appDatabase.expenseDataAccessObject().add(
            EntityExpense(
                id = null,
                name = name,
                installments = installments,
                totalValue = totalValue
            )
        )
    }

    override suspend fun add(domain: Expense) {
        appDatabase.expenseDataAccessObject().add(domain.toEntity())
    }

    override suspend fun deleteById(id: Long) {
        appDatabase.expenseDataAccessObject().findById(id)?.let {
            appDatabase.expenseDataAccessObject().delete(it)
        }
    }

    override suspend fun update(domain: Expense) {
        TODO("Not yet implemented")
    }
}
