package com.dripio.di

import com.dripio.data.AppDatabase
import com.dripio.domain.repository.api.PaymentMethodsRepository
import com.dripio.domain.repository.api.PaymentRepository
import com.dripio.domain.repository.impl.PaymentMethodsRepositoryImpl
import com.dripio.domain.repository.impl.PaymentRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModules = module {
    single { AppDatabase.create(androidContext()) }

    factory<PaymentRepository> { PaymentRepositoryImpl(get()) }
    factory<PaymentMethodsRepository> { PaymentMethodsRepositoryImpl(get()) }
}
