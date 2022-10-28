package com.example.dripio.di

import com.example.dripio.data.AppDatabase
import com.example.dripio.domain.entity.PaymentMethod
import com.example.dripio.domain.repository.api.PaymentMethodsRepository
import com.example.dripio.domain.repository.api.PaymentRepository
import com.example.dripio.domain.repository.impl.PaymentMethodsRepositoryImpl
import com.example.dripio.domain.repository.impl.PaymentRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModules = module {
    single { AppDatabase.create(androidContext()) }

    factory<PaymentRepository> { PaymentRepositoryImpl(get()) }
    factory<PaymentMethodsRepository> { PaymentMethodsRepositoryImpl(get()) }
}
