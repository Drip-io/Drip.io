package com.dripio.presentation.expenses.editor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dripio.extensions.toMoneyStringWithComma
import com.dripio.extensions.visibleOrGone
import com.example.dripio.R
import com.example.dripio.databinding.ActivityExpenseEditorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class ExpenseEditorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityExpenseEditorBinding
    private val viewModel: ExpenseEditorViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExpenseEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViews()
        initObservers()
    }

    private fun saveExpense() {
        val name = binding.tiName.editText?.text?.toString()
        val installments = viewModel.installmentCount.value
        val totalValue = viewModel.totalValue.value

        if(name == null) return
        if(installments == null) return
        if(totalValue == null) return

        viewModel.saveExpense(name = name, installments = installments, totalValue = totalValue) {
            finish()
        }
    }

    private fun initObservers() {
        viewModel.installmentCount.observe(this) {
            binding.tvInstallments.text = "$it"
            onChangeTotalValue()
        }

        viewModel.totalValue.observe(this) {
            binding.tvTotalValue.text = it.toMoneyStringWithComma()
            onChangeTotalValue()
        }
    }

    private fun onChangeTotalValue() {
        val installmentCount = viewModel.installmentCount.value ?: 0
        binding.tvInstallmentValue.text = viewModel.getInstallmentValue().toMoneyStringWithComma()
        binding.groupInstallment.visibleOrGone(viewModel.isInstallmentValid())
        binding.tvInstallmentResume.text = getString(R.string.installment_resume, installmentCount)
    }

    private fun initViews() {
        binding.ibDecrease.setOnClickListener { viewModel.decreaseInstallment() }
        binding.ibIncrease.setOnClickListener { viewModel.increaseInstallment() }
        binding.textInputCurrency.onUpdateValue = {
            viewModel.setTotalValue(it)
        }
        binding.bSave.setOnClickListener {
            saveExpense()
        }
    }
}