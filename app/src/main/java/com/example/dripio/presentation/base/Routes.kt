package com.example.dripio.presentation.base

import android.content.Context
import android.content.Intent
import com.example.dripio.presentation.paymentEditor.PaymentEditorActivity

fun Context.startPaymentEditor(paymentId: Long? = null) {
    val intent = Intent(this, PaymentEditorActivity::class.java)
    paymentId?.let { intent.putExtra(PAYMENT_ID, it) }
    startActivity(intent)
}
