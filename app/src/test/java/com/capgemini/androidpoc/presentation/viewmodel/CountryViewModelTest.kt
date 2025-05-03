package com.capgemini.androidpoc.presentation.viewmodel

import com.capgemini.androidpoc.domain.model.Country
import com.capgemini.androidpoc.domain.usecase.GetCountriesUseCase
import com.capgemini.androidpoc.presentation.state.UiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class CountryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var getCountriesUseCase: GetCountriesUseCase
    private lateinit var viewModel: CountryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCountriesUseCase = mock()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init emits Loading then Success when use case returns data`() = runTest {
        val countries = listOf(Country("India"))
        val flow = flowOf(countries)

        whenever(getCountriesUseCase()).thenReturn(flow)

        viewModel = CountryViewModel(getCountriesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assert(state is UiState.Success)
        assertEquals(countries, (state as UiState.Success).countries)
    }

    @Test
    fun `init emits Loading then Error when use case throws exception`() = runTest {
        val flow = flow<List<Country>> {
            throw RuntimeException("Something went wrong")
        }

        whenever(getCountriesUseCase()).thenReturn(flow)

        viewModel = CountryViewModel(getCountriesUseCase)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.state.value
        assert(state is UiState.Error)
        assertEquals("Failed to load countries", (state as UiState.Error).message)
    }

    @Test
    fun `init emits Loading initially`() = runTest {
        val flow = emptyFlow<List<Country>>()

        whenever(getCountriesUseCase()).thenReturn(flow)

        viewModel = CountryViewModel(getCountriesUseCase)
        // Immediately after init, before collect finishes
        assert(viewModel.state.value is UiState.Loading)
    }
}
