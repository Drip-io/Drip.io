package com.dripio.presentation.base

import android.content.Context
import android.content.Intent
import com.dripio.presentation.paymentEditor.PaymentEditorActivity

fun Context.startPaymentEditor(paymentId: Long? = null) {
    val intent = Intent(this, PaymentEditorActivity::class.java)
    paymentId?.let { intent.putExtra(PAYMENT_ID, it) }
    startActivity(intent)
}
