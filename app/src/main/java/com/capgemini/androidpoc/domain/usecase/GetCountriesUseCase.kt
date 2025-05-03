package com.capgemini.androidpoc.domain.usecase

import com.capgemini.androidpoc.domain.model.Country
import com.capgemini.androidpoc.domain.repository.CountryRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class GetCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    operator fun invoke(): Flow<List<Country>> = flow {
        emit(repository.getCountries())
    }.catch { emit(emptyList()) }
}