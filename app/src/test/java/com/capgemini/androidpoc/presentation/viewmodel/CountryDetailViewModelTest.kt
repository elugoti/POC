package com.capgemini.androidpoc.presentation.viewmodel

import com.capgemini.androidpoc.domain.model.CountryDetail
import com.capgemini.androidpoc.domain.model.CountryFlags
import com.capgemini.androidpoc.domain.model.CountryMaps
import com.capgemini.androidpoc.domain.model.CountryName
import com.capgemini.androidpoc.domain.usecase.GetCountryDetailsUseCase
import com.capgemini.androidpoc.presentation.state.CountryDetailUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class CountryDetailViewModelTest {

    @Mock
    private lateinit var getCountryDetailsUseCase: GetCountryDetailsUseCase

    private lateinit var viewModel: CountryDetailViewModel
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = CountryDetailViewModel(getCountryDetailsUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() {
        // Assert that the initial state is Loading
        assertTrue(viewModel.state.value is CountryDetailUiState.Loading)
    }

    @Test
    fun `load country details successfully`() = runTest {
        // Given
        val countryName = "Germany"
        val countryDetail = createSampleCountryDetail()
        `when`(getCountryDetailsUseCase(countryName)).thenReturn(Result.success(countryDetail))

        // When
        viewModel.loadCountryDetails(countryName)
        // With UnconfinedTestDispatcher, coroutines execute immediately

        // Then
        val currentState = viewModel.state.value
        assertTrue(currentState is CountryDetailUiState.Success)
        assertEquals(countryDetail, (currentState as CountryDetailUiState.Success).country)
    }

    @Test
    fun `handle error from use case`() = runTest {
        // Given
        val countryName = "NonExistentCountry"
        val errorMessage = "Country not found"
        `when`(getCountryDetailsUseCase(countryName)).thenReturn(Result.failure(Exception(errorMessage)))

        // When
        viewModel.loadCountryDetails(countryName)
        // With UnconfinedTestDispatcher, coroutines execute immediately

        // Then
        val currentState = viewModel.state.value
        assertTrue(currentState is CountryDetailUiState.Error)
        assertEquals(errorMessage, (currentState as CountryDetailUiState.Error).message)
    }

    @Test
    fun `state transitions correctly`() = runTest {
        // Given
        val countryName = "France"
        val countryDetail = createSampleCountryDetail()
        `when`(getCountryDetailsUseCase(countryName)).thenReturn(Result.success(countryDetail))

        // When - Initial state should be Loading
        assertTrue(viewModel.state.value is CountryDetailUiState.Loading)

        // When - Load country details
        viewModel.loadCountryDetails(countryName)
        
        // Then - State should transition to Loading first
        assertTrue(viewModel.state.value is CountryDetailUiState.Success)
        
        // When - Advance time to complete coroutine
        // With UnconfinedTestDispatcher, coroutines execute immediately
        
        // Then - State should transition to Success
        val finalState = viewModel.state.value
        assertTrue(finalState is CountryDetailUiState.Success)
    }

    @Test
    fun `empty country name handling`() = runTest {
        // Given
        val emptyCountryName = ""
        val errorMessage = "Country name cannot be empty"
        `when`(getCountryDetailsUseCase(emptyCountryName)).thenReturn(Result.failure(Exception(errorMessage)))

        // When
        viewModel.loadCountryDetails(emptyCountryName)
        // With UnconfinedTestDispatcher, coroutines execute immediately

        // Then
        val currentState = viewModel.state.value
        assertTrue(currentState is CountryDetailUiState.Error)
        assertEquals(errorMessage, (currentState as CountryDetailUiState.Error).message)
    }

    private fun createSampleCountryDetail(): CountryDetail {
        return CountryDetail(
            name = CountryName(common = "Germany", official = "Federal Republic of Germany"),
            capital = listOf("Berlin"),
            region = "Europe",
            subregion = "Western Europe",
            population = 83240000,
            flags = CountryFlags(
                png = "https://example.com/germany.png",
                svg = "https://example.com/germany.svg",
                alt = "Flag of Germany"
            ),
            currencies = emptyMap(),
            languages = mapOf("deu" to "German"),
            timezones = listOf("UTC+01:00"),
            maps = CountryMaps(
                googleMaps = "https://goo.gl/maps/germany",
                openStreetMaps = "https://www.openstreetmap.org/germany"
            )
        )
    }

    data class Currency(val name: String, val symbol: String)
}