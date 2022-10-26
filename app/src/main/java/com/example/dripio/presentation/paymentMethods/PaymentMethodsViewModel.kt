package com.example.dripio.presentation.paymentMethods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.presentation._base.vm.BaseViewModel

class PaymentMethodsViewModel : BaseViewModel() {
    private val _paymentMethods = MutableLiveData(listOf<PaymentMethod>())
    val paymentMethods: LiveData<List<PaymentMethod>> = _paymentMethods
}