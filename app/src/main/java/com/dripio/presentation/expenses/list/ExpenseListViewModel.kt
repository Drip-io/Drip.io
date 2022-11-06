package com.dripio.presentation.expenses.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dripio.domain.entity.Expense
import com.dripio.domain.repository.api.ExpenseRepository
import com.dripio.presentation.base.vm.BaseViewModel

class ExpenseListViewModel(private val expenseRepository: ExpenseRepository) : BaseViewModel() {
    private val _expenses = MutableLiveData(listOf<Expense>())
    val expenses: LiveData<List<Expense>> = _expenses

    fun fetchExpenses() {
        launch {
            val expenses = expenseRepository.findAll()
            _expenses.postValue(expenses)
        }
    }
}