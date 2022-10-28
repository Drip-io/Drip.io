package com.example.dripio.presentation.payments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dripio.domain.entity.Payment
import com.example.dripio.domain.repository.api.PaymentRepository
import com.example.dripio.extensions.setDayMonthTo
import com.example.dripio.extensions.toCalendar
import com.example.dripio.presentation.base.vm.BaseViewModel
import java.util.*

class PaymentsViewModel(private val paymentRepository: PaymentRepository) : BaseViewModel() {
    private val _payments = MutableLiveData(listOf<Payment>())
    val payments: LiveData<List<Payment>> = _payments

    private val _selectedDate = MutableLiveData(Date())
    val selectedDate: LiveData<Date> = _selectedDate

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
