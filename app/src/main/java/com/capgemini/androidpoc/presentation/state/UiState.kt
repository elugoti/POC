package com.capgemini.androidpoc.presentation.state

import com.capgemini.androidpoc.domain.model.Country

sealed class UiState {
    object Loading : UiState()
    data class Success(val countries: List<Country>) : UiState()
    data class Error(val message: String) : UiState()
}