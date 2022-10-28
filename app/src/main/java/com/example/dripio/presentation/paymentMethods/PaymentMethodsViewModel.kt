package com.example.dripio.presentation.paymentMethods

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.presentation.base.vm.BaseViewModel

class PaymentMethodsViewModel : BaseViewModel() {
    private val _paymentMethods = MutableLiveData(listOf<PaymentMethod>())
    val paymentMethods: LiveData<List<PaymentMethod>> = _paymentMethods

    private val _selectedColor = MutableLiveData("#FF0000")
    val selectedColor: LiveData<String> = _selectedColor

    fun setSelectedColor(color: String) {
        _selectedColor.value = color
    }
}
