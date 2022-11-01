package com.dripio.presentation.paymentEditor

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dripio.domain.entity.PaymentMethod
import com.dripio.extensions.closeKeyboard
import com.dripio.extensions.formatToDayMonthYear
import com.dripio.extensions.setText
import com.dripio.extensions.toMoneyStringWithPeriod
import com.dripio.presentation.base.PAYMENT_ID
import com.dripio.presentation.base.openPaymentEditor
import com.example.dripio.R
import com.example.dripio.databinding.ActivityPaymentEditorBinding
import com.google.android.material.datepicker.MaterialDatePicker
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class PaymentEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPaymentEditorBinding
    private val viewModel: PaymentEditorViewModel by viewModel()

    private val paymentId by lazy {
        intent.extras?.getLong(PAYMENT_ID)
    }

    private fun isEditorMode() = paymentId != null

    private fun initViewModel() {
        paymentId?.let {
            viewModel.fetchPayment(it)
        }
    }

    private val paymentSelectorResult = openPaymentEditor { paymentMethodId ->
        paymentMethodId?.let {
            viewModel.fetchPaymentMethod(it)
        }
    }

    private fun initObservers() {
        viewModel.payment.observe(this) { payment ->
            payment?.let { payment ->
                binding.tiName.editText?.editableText?.setText(payment.name)
                viewModel.selectDate(payment.paidAt)
                viewModel.selectValue(payment.paymentValue)
                payment.paymentMethod?.let { paymentMethod ->
                    setViewFlipperToColorCard(paymentMethod)
                }
            }
        }

        binding.textInputCurrency.onUpdateValue = {
            viewModel.selectValue(it)
        }

        viewModel.selectedDate.observe(this) {
            setDateFieldText(it.formatToDayMonthYear())
        }

        viewModel.canSave.observe(this) {
            binding.bSave.isEnabled = it
        }

        viewModel.paymentMethod.observe(this) { paymentMethod ->
            paymentMethod?.let {
                setViewFlipperToColorCard(it)
            } ?: run {
                binding.viewFlipperSelectPaymentMethod.displayedChild = 0
            }
        }
    }

    private fun setViewFlipperToColorCard(paymentMethod: PaymentMethod) {
        binding.viewFlipperSelectPaymentMethod.displayedChild = 1
        binding.colorCard.tvColor.text = paymentMethod.name
        binding.colorCard.ivColor.setColorFilter(Color.parseColor(paymentMethod.color))
        binding.colorCard.ivClose.setOnClickListener {
            viewModel.clearPaymentMethod()
        }
    }

    private fun initViews() {
        setupActionBar()

        binding.tiDate.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePicker()
            }
        }

        initSaveButton()

        binding.viewFlipperSelectPaymentMethod.setOnClickListener {
            paymentSelectorResult.launch(Unit)
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun initSaveButton() {
        binding.bSave.text =
            if (isEditorMode()) getString(R.string.save) else getString(R.string.register)

        binding.bSave.setOnClickListener {
            if (isEditorMode()) {
                updatePayment()
            } else {
                registerPayment()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        initObservers()
        initViewModel()
    }

    private fun updatePayment() {
        val value = viewModel.selectedValue.value
        val name = binding.tiName.editText?.editableText?.toString()
        val paidDate = viewModel.selectedDate.value
        val paymentMethod = viewModel.paymentMethod.value

        viewModel.updatePayment(name = name, paidDate = paidDate, paymentValue = value, paymentMethod = paymentMethod)
        finish()
    }

    private fun registerPayment() {
        val value = viewModel.selectedValue.value
        val name = binding.tiName.editText?.editableText?.toString()
        val paidDate = viewModel.selectedDate.value
        val paymentMethod = viewModel.paymentMethod.value

        if (value == null || name == null || paidDate == null) {
            return
        }

        viewModel.canSave(false)
        viewModel.createPayment(name, null, value, paymentMethod, null, paidDate)
        finish()
    }

    private fun setDateFieldText(text: String) {
        binding.tiDate.editText?.editableText?.clear()
        binding.tiDate.editText?.editableText?.insert(0, text)
    }

    private fun clearDateFieldFocus() {
        binding.tiDate.editText?.clearFocus()
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
        val builtDatePicker = datePicker.build()

        builtDatePicker.addOnPositiveButtonClickListener {
            viewModel.selectDate(Date(it))
            clearDateFieldFocus()
        }
        builtDatePicker.addOnCancelListener {
            clearDateFieldFocus()
        }
        builtDatePicker.addOnDismissListener {
            clearDateFieldFocus()
        }
        builtDatePicker.show(supportFragmentManager, "datePicker")
    }
}
