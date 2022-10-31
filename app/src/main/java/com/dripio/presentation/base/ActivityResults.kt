package com.dripio.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.dripio.presentation.paymentMethods.list.PaymentMethodListActivity

fun AppCompatActivity.openPaymentEditor(callback: (Long?) -> Unit) =
    registerForActivityResult(PaymentMethodListActivity.PaymentSelectorActivityContract()) {
        callback.invoke(it)
    }