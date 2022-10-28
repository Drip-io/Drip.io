package com.example.dripio.presentation.paymentEditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dripio.domain.entity.Category
import com.example.dripio.domain.entity.Expense
import com.example.dripio.domain.entity.Payment
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.domain.repository.api.PaymentRepository
import com.example.dripio.presentation.base.vm.BaseViewModel
import java.util.*

class PaymentEditorViewModel(private val paymentRepository: PaymentRepository) : BaseViewModel() {

    private val _canSave = MutableLiveData(true)
    val canSave: LiveData<Boolean> = _canSave

    private val _selectedDate = MutableLiveData(Date())
    val selectedDate: LiveData<Date> = _selectedDate

    private val _selectedValue = MutableLiveData(0F)
    val selectedValue: LiveData<Float> = _selectedValue

    private val _payment = MutableLiveData<Payment?>(null)
    val payment: LiveData<Payment?> = _payment

    fun selectDate(date: Date) {
        _selectedDate.postValue(date)
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

    fun createPayment(name: String?, expense: Expense?, paymentValue: Float, paymentMethod: PaymentMethod?, category: Category?, paidDate: Date) {
        launch {
            paymentRepository.add(name = name, expenseId = expense?.id, paymentValue = paymentValue, paymentMethodId = paymentMethod?.id, categoryId = category?.id, paidDate = paidDate)
            canSave(true)
        }
    }

    fun updatePayment(name: String? = null, expense: Expense? = null, paymentValue: Float? = null, paymentMethod: PaymentMethod? = null, category: Category? = null, paidDate: Date? = null) {
        launch {
            val payment = payment.value
            if (payment != null) {
                var lastPayment: Payment = payment

                if (name != null) lastPayment = lastPayment.copy(name = name)
                if (expense != null) lastPayment = lastPayment.copy(expense = expense)
                if (paymentValue != null) lastPayment = lastPayment.copy(paymentValue = paymentValue)
                if (paymentMethod != null) lastPayment = lastPayment.copy(paymentMethod = paymentMethod)
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
