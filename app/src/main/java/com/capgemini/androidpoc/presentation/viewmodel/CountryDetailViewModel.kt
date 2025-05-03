package com.capgemini.androidpoc.presentation.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capgemini.androidpoc.domain.usecase.GetCountryDetailsUseCase
import com.capgemini.androidpoc.presentation.state.CountryDetailUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class CountryDetailViewModel @Inject constructor(
    private val getCountryDetails: GetCountryDetailsUseCase
) : ViewModel() {
    private val _state = mutableStateOf<CountryDetailUiState>(CountryDetailUiState.Loading)
    val state: State<CountryDetailUiState> = _state

    fun loadCountryDetails(name: String) {
        viewModelScope.launch {
            _state.value = CountryDetailUiState.Loading
            getCountryDetails(name).fold(
                onSuccess = { country ->
                    _state.value = CountryDetailUiState.Success(country)
                },
                onFailure = { exception ->
                    _state.value = CountryDetailUiState.Error(exception.message ?: "Unknown error")
                }
            )
        }
    }
}