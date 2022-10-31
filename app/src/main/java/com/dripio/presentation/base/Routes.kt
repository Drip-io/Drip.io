package com.dripio.presentation.base

import android.content.Context
import android.content.Intent
import com.dripio.presentation.paymentEditor.PaymentEditorActivity
import com.dripio.presentation.paymentMethods.editor.PaymentMethodEditorActivity

fun Context.startPaymentEditor(paymentId: Long? = null) {
    val intent = Intent(this, PaymentEditorActivity::class.java)
    paymentId?.let { intent.putExtra(PAYMENT_ID, it) }
    startActivity(intent)
}

fun Context.startPaymentMethodEditor() {
    val intent = Intent(this, PaymentMethodEditorActivity::class.java)
    startActivity(intent)
}
