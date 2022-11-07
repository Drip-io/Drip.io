package com.dripio.presentation.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.dripio.domain.entity.Payment
import com.dripio.extensions.formatToDayMonthYear
import com.dripio.extensions.toMoneyStringWithComma
import com.dripio.extensions.visibleOrGone
import com.dripio.presentation.payments.PaymentsViewModel
import com.example.dripio.R
import com.example.dripio.databinding.BottomSheetPaymentDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class PaymentDetailBottomSheet(private val payment: Payment) : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetPaymentDetailBinding
    private val viewModel: PaymentsViewModel by sharedViewModel()
    private var onDelete: (() -> Unit)? = null
    private var onEdit: ((Payment) -> Unit)? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context != null) {
            binding = BottomSheetPaymentDetailBinding.inflate(inflater)
            return binding.root
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        initObserver()
        initViewModel()
    }

    private fun initViews() {
        binding.tvPaymentName.text = payment.name
        binding.tvPaymentValue.text = payment.paymentValue.toMoneyStringWithComma()
        binding.tvPaidAt.text = payment.paidAt.formatToDayMonthYear()
        binding.bDelete.setOnClickListener {
            deletePayment()
            onDelete?.invoke()
            dismiss()
        }
        binding.bEdit.setOnClickListener {
            onEdit?.invoke(payment)
            dismiss()
        }
    }

    private fun initObserver() {
        viewModel.loadedExpense.observe(viewLifecycleOwner) { expense ->
            binding.groupExpense.visibleOrGone(expense != null)

            expense?.let {
                binding.tvExpense.text = context?.getString(R.string.payment_resume_expense_template, it.name)
                val paidInstallments = it.relatedPayments.size
                val totalInstallments = it.installments
                binding.tvInstallmentStatus.text = context?.getString(R.string.payment_resume_expense_installment, paidInstallments, totalInstallments)
            }
        }
    }

    private fun initViewModel() {
        viewModel.loadExpenseForPaymentInfo(payment)
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
