package com.dripio.presentation.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.dripio.domain.entity.Payment
import com.dripio.extensions.formatToDayMonthYear
import com.dripio.extensions.toMoneyStringWithComma
import com.dripio.presentation.payments.PaymentsViewModel
import com.example.dripio.databinding.BottomSheetPaymentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PaymentDetailBottomSheet(private val payment: Payment) : BottomSheetDialogFragment() {

    private val viewModel: PaymentsViewModel by sharedViewModel()
    private var onDelete: (() -> Unit)? = null
    private var onEdit: ((Payment) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = BottomSheetPaymentDetailBinding.inflate(inflater)
        view.tvPaymentName.text = payment.name
        view.tvPaymentValue.text = payment.paymentValue.toMoneyStringWithComma()
        view.tvPaidAt.text = payment.paidAt.formatToDayMonthYear()
        view.bDelete.setOnClickListener {
            deletePayment()
            onDelete?.invoke()
            dismiss()
        }
        view.bEdit.setOnClickListener {
            onEdit?.invoke(payment)
            dismiss()
        }
        return view.root
    }

    private fun deletePayment() {
        viewModel.deletePayment(payment.id)
    }

    fun setOnDelete(callback: () -> Unit) {
        onDelete = callback
    }

    fun setOnEdit(callback: (Payment) -> Unit) {
        onEdit = callback
    }

    companion object {
        const val TAG = "PaymentDetailModal"

        fun open(supportFragmentManager: FragmentManager, payment: Payment): PaymentDetailBottomSheet {
            val paymentDetailModal = PaymentDetailBottomSheet(payment)
            paymentDetailModal.show(supportFragmentManager, TAG)
            return paymentDetailModal
        }
    }
}
