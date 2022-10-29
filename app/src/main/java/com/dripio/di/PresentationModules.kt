package com.dripio.di

import com.dripio.presentation.paymentEditor.PaymentEditorViewModel
import com.dripio.presentation.paymentMethods.PaymentMethodsViewModel
import com.dripio.presentation.payments.PaymentsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModules = module {
    viewModel { PaymentEditorViewModel(get(), get()) }
    viewModel { PaymentsViewModel(get()) }
    viewModel { PaymentMethodsViewModel(get()) }
}
