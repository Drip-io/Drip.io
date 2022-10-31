package com.dripio.vm

import com.dripio.domain.repository.api.PaymentRepository
import com.dripio.presentation.payments.PaymentsViewModel
import io.mockk.MockKAnnotations
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.KoinTest

class PaymentsViewModelTest: KoinTest {

    @MockK private lateinit var viewModel: PaymentsViewModel
    @MockK private lateinit var repository: PaymentRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `givenPaymentId whenDeletePayment thenDeletePayment`() {
        val id = 0L

        every { viewModel.deletePayment(id) } just Runs
        coEvery { repository.deleteById(id) } just Runs

        viewModel.deletePayment(id)

        coVerify { repository.deleteById(id) }
    }
}