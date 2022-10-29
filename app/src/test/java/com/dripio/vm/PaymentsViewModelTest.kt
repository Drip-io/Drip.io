package com.dripio.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dripio.di.appModules
import com.dripio.domain.repository.api.PaymentRepository
import com.dripio.presentation.payments.PaymentsViewModel
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@RunWith(JUnit4::class)
class PaymentsViewModelTest: KoinTest {
    val paymentsViewModel: PaymentsViewModel by inject()
    val paymentRepository: PaymentRepository = mockk()

    @Before
    fun before() {
        startKoin {
            androidContext(mockk())
            modules(appModules)
        }
    }

    @Test
    fun `givenPaymentId whenDeletePayment thenDeletePayment`() {
        val id: Long = 0L

        coEvery { paymentRepository.deleteById(id) } just Runs
        paymentsViewModel.deletePayment(id)
        coVerify { paymentRepository.deleteById(id) }
    }
}