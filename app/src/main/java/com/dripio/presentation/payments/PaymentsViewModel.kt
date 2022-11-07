package com.dripio.presentation.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dripio.domain.entity.Expense
import com.dripio.domain.entity.Payment
import com.dripio.domain.repository.api.ExpenseRepository
import com.dripio.domain.repository.api.PaymentRepository
import com.dripio.extensions.setDayMonthTo
import com.dripio.extensions.toCalendar
import com.dripio.presentation.base.vm.BaseViewModel
import java.util.*

class PaymentsViewModel(
    private val paymentRepository: PaymentRepository,
    private val expenseRepository: ExpenseRepository
) : BaseViewModel() {

    private val _payments = MutableLiveData(listOf<Payment>())
    val payments: LiveData<List<Payment>> = _payments

    private val _loadedExpense = MutableLiveData<Expense?>()
    val loadedExpense: LiveData<Expense?> = _loadedExpense

    private val _selectedDate = MutableLiveData(Date())
    val selectedDate: LiveData<Date> = _selectedDate

    fun loadExpenseForPaymentInfo(payment: Payment) {
        launch {
            payment.expense?.id?.let {
                _loadedExpense.postValue(expenseRepository.find(it))
            }
        }
    }

    fun clearPaymentExpense() {
        _loadedExpense.value = null
    }

    fun setSelectedDate(date: Date) {
        _selectedDate.value = date

        launch {
            val firstDay = date.toCalendar().getMinimum(Calendar.DAY_OF_MONTH)
            val lastDay = date.toCalendar().getMaximum(Calendar.DAY_OF_MONTH)
            val firstDate = date.setDayMonthTo(firstDay)
            val lastDate = date.setDayMonthTo(lastDay)
            val payments = paymentRepository.findAllWithinDates(firstDate, lastDate)
            _payments.postValue(payments)
        }
    }

    fun deletePayment(id: Long) {
        launch {
            paymentRepository.deleteById(id)
        }
    }
}
