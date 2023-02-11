package com.example.onlineshop.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.onlineshop.MainCoroutineRule
import com.example.onlineshop.asDispatcherProvider
import com.example.onlineshop.base.Failed
import com.example.onlineshop.base.Loading
import com.example.onlineshop.domain.GetOrdersUseCase
import com.example.onlineshop.domain.Order
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class OrdersViewModelTest {

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @RelaxedMockK
    lateinit var getOrdersUseCase: GetOrdersUseCase

    @RelaxedMockK
    private lateinit var mockOrders: List<Order>

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    private fun createViewModel() =
        OrdersViewModel(getOrdersUseCase, mainCoroutineRule.getDispatcher().asDispatcherProvider())

    @Test
    fun `when viewModel is created, it should start loading orders`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { getOrdersUseCase.execute() } coAnswers {
                delay(1000)
                flow { mockOrders }
            }
            createViewModel()
            delay(100)
            coVerify(exactly = 1) { getOrdersUseCase.execute() }
        }

    @Test
    fun `when viewModel is loading data, the loadable orders value should be Loading`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { getOrdersUseCase.execute() } coAnswers {
                delay(1000)
                flow { mockOrders }
            }
            val vm = createViewModel()
            delay(100)
            assertEquals(Loading, vm.loadableOrders.value)
        }

    @Test
    fun `when viewModel has loaded data, it should adds all loaded data to loadable orders list`() =
        mainCoroutineRule.runBlockingTest {
            coEvery { getOrdersUseCase.execute() } coAnswers {
                delay(100)
                flow { mockOrders }
            }
            val vm = createViewModel()
            delay(1000)

            assertEquals(mockOrders, vm.loadableOrders.value?.data)

        }

    @Test
    fun `when viewModel failed to load the list, loadable orders must have proper throwable data`() =
        mainCoroutineRule.runBlockingTest {
            val customException = Exception("exception")
            coEvery { getOrdersUseCase.execute() } coAnswers { throw customException }
            val vm = createViewModel()
            delay(100)
            assertEquals(customException, (vm.loadableOrders.value as Failed).throwble)
        }


}