package com.dripio.presentation.base

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.dripio.domain.entity.PaymentMethod
import com.example.dripio.R

fun Context.showConfirmDeletePaymentMethod(
    paymentMethod: PaymentMethod,
    onConfirm: (PaymentMethod) -> Unit
) {
    val builder = AlertDialog.Builder(this)
    val title = getString(R.string.confirm_delete_payment_method_title)
    builder.setTitle(title)
    val message = getString(R.string.confirm_delete_payment_method_message, paymentMethod.name)
    builder.setMessage(message)
    val delete = getString(R.string.delete)
    builder.setPositiveButton(delete) { _, _ ->
        onConfirm.invoke(paymentMethod)
    }
    val cancel = getString(R.string.cancel)
    builder.setNegativeButton(cancel) { dialog, _ ->
        dialog.dismiss()
    }
    return builder.create().show()
}
