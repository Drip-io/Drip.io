package com.dripio.presentation.base

import android.content.Context
import android.content.Intent
import com.dripio.presentation.expenses.editor.ExpenseEditorActivity
import com.dripio.presentation.expenses.list.ExpenseListActivity
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

fun Context.startExpenseList() {
    val intent = Intent(this, ExpenseListActivity::class.java)
    startActivity(intent)
}

fun Context.startExpenseEditor() {
    val intent = Intent(this, ExpenseEditorActivity::class.java)
    startActivity(intent)
}
