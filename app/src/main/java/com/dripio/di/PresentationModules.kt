package com.dripio.di

import com.dripio.presentation.expenses.editor.ExpenseEditorViewModel
import com.dripio.presentation.expenses.list.ExpenseListViewModel
import com.dripio.presentation.paymentEditor.PaymentEditorViewModel
import com.dripio.presentation.paymentMethods.editor.vm.PaymentMethodEditorViewModel
import com.dripio.presentation.paymentMethods.list.vm.PaymentMethodListViewModel
import com.dripio.presentation.payments.PaymentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel { PaymentEditorViewModel(get(), get(), get()) }
    viewModel { PaymentsViewModel(get()) }
    viewModel { PaymentMethodListViewModel(get()) }
    viewModel { PaymentMethodEditorViewModel(get()) }
    viewModel { ExpenseListViewModel(get()) }
    viewModel { ExpenseEditorViewModel(get()) }
}
