package com.example.dripio.presentation.paymentMethods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.domain.repository.api.PaymentMethodsRepository
import com.example.dripio.presentation.base.vm.BaseViewModel

class PaymentMethodsViewModel(private val paymentMethodsRepository: PaymentMethodsRepository) : BaseViewModel() {
    private val _paymentMethods = MutableLiveData(listOf<PaymentMethod>())
    val paymentMethods: LiveData<List<PaymentMethod>> = _paymentMethods

    private val _selectedColor = MutableLiveData("#FF0000")
    val selectedColor: LiveData<String> = _selectedColor

    fun setSelectedColor(color: String) {
        _selectedColor.value = color
    }

    fun addPaymentMethod(name: String, color: String) {
        launch {
            paymentMethodsRepository.add(name = name, color = color)
        }
    }

    fun fetchPaymentMethods() {
        launch {
            _paymentMethods.postValue(paymentMethodsRepository.findAll())
        }
    }
}
