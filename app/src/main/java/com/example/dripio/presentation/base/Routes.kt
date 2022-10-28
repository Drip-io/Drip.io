package com.example.dripio.presentation.base

import android.content.Context
import android.content.Intent
import com.example.dripio.presentation.paymentEditor.PaymentEditorActivity
import com.example.dripio.presentation.paymentMethods.PaymentMethodsHostActivity

const val PAYMENT_ID = "PAYMENT_ID"

fun Context.startPaymentEditor(paymentId: Long? = null) {
    val intent = Intent(this, PaymentEditorActivity::class.java)
    paymentId?.let { intent.putExtra(PAYMENT_ID, it) }
    startActivity(intent)
}