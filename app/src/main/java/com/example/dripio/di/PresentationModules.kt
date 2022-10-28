package com.example.dripio.di

import com.example.dripio.presentation.paymentEditor.PaymentEditorViewModel
import com.example.dripio.presentation.paymentMethods.PaymentMethodsViewModel
import com.example.dripio.presentation.payments.PaymentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel { PaymentEditorViewModel(get(), get()) }
    viewModel { PaymentsViewModel(get()) }
    viewModel { PaymentMethodsViewModel(get()) }
}
