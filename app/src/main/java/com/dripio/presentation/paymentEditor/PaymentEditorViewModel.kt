package com.dripio.presentation.paymentEditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dripio.domain.entity.Category
import com.dripio.domain.entity.Expense
import com.dripio.domain.entity.Payment
import com.dripio.domain.entity.PaymentMethod
import com.dripio.domain.repository.api.ExpenseRepository
import com.dripio.domain.repository.api.PaymentMethodsRepository
import com.dripio.domain.repository.api.PaymentRepository
import com.dripio.presentation.base.vm.BaseViewModel
import java.util.*

class PaymentEditorViewModel(
    private val paymentRepository: PaymentRepository,
    private val paymentMethodsRepository: PaymentMethodsRepository,
    private val expenseRepository: ExpenseRepository
) : BaseViewModel() {

    private val _canSave = MutableLiveData(true)
    val canSave: LiveData<Boolean> = _canSave

    private val _selectedDate = MutableLiveData(Date())
    val selectedDate: LiveData<Date> = _selectedDate

    private val _selectedValue = MutableLiveData(0F)
    val selectedValue: LiveData<Float> = _selectedValue

    private val _payment = MutableLiveData<Payment?>(null)
    val payment: LiveData<Payment?> = _payment

    private val _paymentMethod = MutableLiveData<PaymentMethod?>(null)
    val paymentMethod: LiveData<PaymentMethod?> = _paymentMethod

    private val _expense = MutableLiveData<Expense?>(null)
    val expense: LiveData<Expense?> = _expense

    fun selectDate(date: Date) {
        _selectedDate.postValue(date)
    }

    fun setExpense(expense: Expense) {
        _expense.value = expense
    }

    fun clearExpense() {
        _expense.value = null
    }

    fun clearPaymentMethod() {
        _paymentMethod.value = null
    }

    fun fetchExpense(id: Long) {
        launch {
            _expense.postValue(expenseRepository.find(id))
        }
    }

    fun fetchPaymentMethod(id: Long) {
        launch {
            _paymentMethod.postValue(paymentMethodsRepository.find(id))
        }
    }

    fun selectValue(f: Float) {
        _selectedValue.value = f
    }

    fun fetchPayment(paymentId: Long) {
        launch {
            paymentRepository.find(paymentId)?.let {
                _payment.postValue(it)
            }
        }
    }

    fun createPayment(
        name: String?,
        expense: Expense?,
        paymentValue: Float,
        paymentMethod: PaymentMethod?,
        category: Category?,
        paidDate: Date
    ) {
        launch {
            paymentRepository.add(
                name = name,
                expenseId = expense?.id,
                paymentValue = paymentValue,
                paymentMethodId = paymentMethod?.id,
                categoryId = category?.id,
                paidDate = paidDate
            )
            canSave(true)
        }
    }

    fun updatePayment(
        name: String? = null,
        expense: Expense? = null,
        paymentValue: Float? = null,
        paymentMethod: PaymentMethod? = null,
        category: Category? = null,
        paidDate: Date? = null
    ) {
        launch {
            val payment = payment.value
            if (payment != null) {
                var lastPayment: Payment = payment

                if (name != null) lastPayment = lastPayment.copy(name = name)
                if (expense != null) lastPayment = lastPayment.copy(expense = expense)
                if (paymentValue != null) {
                    lastPayment =
                        lastPayment.copy(paymentValue = paymentValue)
                }

                lastPayment =
                    if (paymentMethod != null) lastPayment.copy(paymentMethod = paymentMethod) else lastPayment.copy(
                        paymentMethod = null
                    )
                if (category != null) lastPayment = lastPayment.copy(category = category)
                if (paidDate != null) lastPayment = lastPayment.copy(paidAt = paidDate)
                lastPayment = lastPayment.copy(updatedAt = Date())
                paymentRepository.update(lastPayment)
            }
        }
    }

    fun canSave(state: Boolean) {
        _canSave.value = state
    }
}
