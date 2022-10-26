package com.example.dripio.presentation.paymentEditor

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import com.example.dripio.R
import com.example.dripio.databinding.ActivityPaymentEditorBinding
import com.example.dripio.extensions.closeKeyboard
import com.example.dripio.extensions.setText
import com.example.dripio.extensions.toCalendar
import com.example.dripio.extensions.toMoneyStringWithPeriod
import com.example.dripio.presentation._base.PAYMENT_ID
import com.example.dripio.presentation.paymentMethods.PaymentSelectorActivityContract
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

    val paymentSelectorResult = registerForActivityResult(PaymentSelectorActivityContract()) {

    }

    private fun initObservers() {
        viewModel.payment.observe(this) { payment ->
            payment?.let {
                binding.tiName.editText?.editableText?.setText(it.name)
                viewModel.selectDate(payment.paidAt)
                viewModel.selectValue(payment.paymentValue)
            }
        }

        viewModel.selectedValue.observe(this) { selectedValue ->
            selectedValue.toString().toFloatOrNull()?.let {
                setValueFieldText(it.toMoneyStringWithPeriod())
            } ?: run {
                setValueFieldText("0.00")
            }
        }

        viewModel.selectedDate.observe(this) {
            val calendar = it.toCalendar()
            val month = calendar[Calendar.MONTH] + 1
            val year = calendar[Calendar.YEAR]
            val formattedDate = "$month / $year"
            setDateFieldText(formattedDate)
        }

        viewModel.canSave.observe(this) {
            binding.bSave.isEnabled = it
        }
    }

    private fun initViews() {
        setupActionBar()

        binding.tiDate.editText?.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                showDatePicker()
            }
        }

        binding.tiValue.editText?.setOnFocusChangeListener { _, _ ->
            binding.tiValue.editText?.text?.toString()?.toFloatOrNull()?.let {
                viewModel.selectValue(it)
            } ?: run {
                viewModel.selectValue(0F)
            }
        }

        binding.tiValue.editText?.let {
            it.setOnEditorActionListener { _, _, _ ->
                it.clearFocus()
                it.closeKeyboard()
                return@setOnEditorActionListener true
            }
        }


        initSaveButton()

        binding.tvSelectPaymentMethod.setOnClickListener {
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

        viewModel.updatePayment(name = name, paidDate = paidDate, paymentValue = value)
        finish()
    }

    private fun registerPayment() {
        val value = viewModel.selectedValue.value
        val name = binding.tiName.editText?.editableText?.toString()
        val paidDate = viewModel.selectedDate.value

        if (value == null || name == null || paidDate == null) {
            return
        }

        viewModel.canSave(false)
        viewModel.createPayment(name, null, value, null, null, paidDate)
        finish()
    }

    private fun setDateFieldText(text: String) {
        binding.tiDate.editText?.editableText?.clear()
        binding.tiDate.editText?.editableText?.insert(0, text)
    }

    private fun setValueFieldText(text: String) {
        binding.tiValue.editText?.editableText?.setText(text)
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