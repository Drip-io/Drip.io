package com.dripio.presentation.payments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dripio.databinding.ActivityPaymentsBinding
import com.dripio.domain.entity.Payment
import com.dripio.extensions.formatToMonthYear
import com.dripio.extensions.toMoneyStringWithComma
import com.dripio.presentation.base.startPaymentEditor
import com.dripio.presentation.bottomSheet.PaymentDetailBottomSheet
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PaymentsActivity : AppCompatActivity(), PaymentListAdapter.Callback {
    private lateinit var binding: ActivityPaymentsBinding
    private lateinit var paymentListAdapter: PaymentListAdapter
    private val viewModel: PaymentsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvPay.setOnClickListener {
            startPaymentEditor()
        }

        setSupportActionBar(binding.toolbar)

        binding.toolbar.setOnClickListener {
            showDatePicker()
        }

        setupPaymentList()

        viewModel.payments.observe(this) { payments ->
            paymentListAdapter.payments = payments
            binding.tvTotalValue.text = payments.sumOf { it.paymentValue.toDouble() }.toFloat().toMoneyStringWithComma()
        }

        viewModel.selectedDate.observe(this) {
            binding.toolbar.subtitle = it.formatToMonthYear()
        }

        viewModel.loading.observe(this) {
            // binding.viewLoading.root.visibleOrGone(it)
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
        val builtDatePicker = datePicker.build()

        builtDatePicker.addOnPositiveButtonClickListener {
            viewModel.setSelectedDate(Date(it))
        }
        builtDatePicker.show(supportFragmentManager, "datePicker")
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSelectedDate(Date())
    }

    private fun setupPaymentList() {
        paymentListAdapter = PaymentListAdapter(this, this)
        binding.rvPayments.adapter = paymentListAdapter
        binding.rvPayments.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onClickListener(payment: Payment) {
        val paymentDetailModal = PaymentDetailBottomSheet.open(supportFragmentManager, payment)

        paymentDetailModal.setOnDelete {
            viewModel.setSelectedDate(Date())
        }

        paymentDetailModal.setOnEdit {
            startPaymentEditor(it.id)
        }
    }
}
