package com.capgemini.androidpoc.presentation.state

import com.capgemini.androidpoc.domain.model.CountryDetail

sealed interface CountryDetailUiState {
    data object Loading : CountryDetailUiState
    data class Success(val country: CountryDetail) : CountryDetailUiState
    data class Error(val message: String) : CountryDetailUiState
}