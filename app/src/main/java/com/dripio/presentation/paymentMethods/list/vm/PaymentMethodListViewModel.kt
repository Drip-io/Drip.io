package com.dripio.presentation.paymentMethods.list.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dripio.domain.entity.PaymentMethod
import com.dripio.domain.repository.api.PaymentMethodsRepository
import com.dripio.presentation.base.vm.BaseViewModel

class PaymentMethodListViewModel(private val paymentMethodsRepository: PaymentMethodsRepository) : BaseViewModel() {

    private val _paymentMethods = MutableLiveData(listOf<PaymentMethod>())
    val paymentMethods: LiveData<List<PaymentMethod>> = _paymentMethods

    fun fetchPaymentMethods() {
        launch {
            _paymentMethods.postValue(paymentMethodsRepository.findAll())
        }
    }
}
