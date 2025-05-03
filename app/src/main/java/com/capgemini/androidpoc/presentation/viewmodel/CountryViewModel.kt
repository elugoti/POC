package com.capgemini.androidpoc.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capgemini.androidpoc.domain.model.Country
import com.capgemini.androidpoc.domain.usecase.GetCountriesUseCase
import com.capgemini.androidpoc.presentation.state.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class CountryViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Loading)
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getCountriesUseCase()
                .onStart { _state.value = UiState.Loading }
                .catch { _state.value = UiState.Error("Failed to load countries") }
                .collect { countries ->
                    _state.value = UiState.Success(countries)
                }
        }
    }
}