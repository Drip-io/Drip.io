package com.example.dripio.presentation.paymentMethods

import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class PaymentSelectorActivityContract : ActivityResultContract<Unit, Long?>() {
    override fun createIntent(context: Context, input: Unit): Intent = Intent(context, PaymentMethodsHostActivity::class.java)

    override fun parseResult(resultCode: Int, intent: Intent?): Long? {
        return null
    }
}
