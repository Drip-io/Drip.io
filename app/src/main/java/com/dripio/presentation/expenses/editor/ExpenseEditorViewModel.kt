package com.dripio.presentation.expenses.editor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dripio.domain.repository.api.ExpenseRepository
import com.dripio.presentation.base.vm.BaseViewModel
import java.lang.Integer.max

class ExpenseEditorViewModel(private val expenseRepository: ExpenseRepository) : BaseViewModel() {

    private val _installmentCount = MutableLiveData(12)
    val installmentCount: LiveData<Int> = _installmentCount

    private val _totalValue = MutableLiveData(0.00F)
    val totalValue: LiveData<Float> = _totalValue

    fun saveExpense(name: String, installments: Int?, totalValue: Float, callback: (() -> Unit)? = null) {
        launch {
            val installmentCount = installments ?: 0
            val tempInstallmentCount = if(installmentCount > 1) installmentCount else null
            expenseRepository.add(name = name, installments = tempInstallmentCount, totalValue = totalValue)
            callback?.invoke()
        }
    }

    fun getInstallmentValue(): Float {
        val currentValue = totalValue.value ?: 0F
        val currentInstallments = installmentCount.value ?: 0

        if (currentInstallments < 2) return currentValue
        if (currentValue == 0F) return 0F

        return currentValue / currentInstallments
    }

    fun isInstallmentValid(): Boolean {
        val currentValue = totalValue.value ?: 0F
        val currentInstallments = installmentCount.value ?: 0

        return currentValue > 0F && currentInstallments > 1
    }

    fun setTotalValue(value: Float) {
        _totalValue.value = value
    }

    fun setInstallmentCount(count: Int) {
        _installmentCount.value = count
    }

    fun changeInstallmentBy(amount: Int) {
        val currentValue = installmentCount.value ?: 0
        setInstallmentCount(max(0, currentValue + amount))
    }

    fun increaseInstallment() {
        changeInstallmentBy(1)
    }

    fun decreaseInstallment() {
        changeInstallmentBy(-1)
    }
}