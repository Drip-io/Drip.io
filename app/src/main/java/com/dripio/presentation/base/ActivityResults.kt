package com.dripio.presentation.base

import androidx.appcompat.app.AppCompatActivity
import com.dripio.presentation.expenses.list.ExpenseListActivity
import com.dripio.presentation.paymentMethods.list.PaymentMethodListActivity

fun AppCompatActivity.openPaymentMethodList(callback: (Long?) -> Unit) =
    registerForActivityResult(PaymentMethodListActivity.PaymentMethodSelectorActivityContract()) {
        callback.invoke(it)
    }

fun AppCompatActivity.openExpenseList(callback: (Long?) -> Unit) =
    registerForActivityResult(ExpenseListActivity.ExpenseListActivityContract()) {
        callback.invoke(it)
    }