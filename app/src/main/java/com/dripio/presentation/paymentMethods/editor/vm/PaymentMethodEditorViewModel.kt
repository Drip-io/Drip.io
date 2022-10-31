package com.dripio.presentation.paymentMethods.editor.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dripio.domain.repository.api.PaymentMethodsRepository
import com.dripio.presentation.base.vm.BaseViewModel

class PaymentMethodEditorViewModel(private val paymentMethodsRepository: PaymentMethodsRepository) : BaseViewModel() {
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
}
