package com.capgemini.androidpoc.domain.usecase

import com.capgemini.androidpoc.domain.model.CountryDetail
import com.capgemini.androidpoc.domain.repository.CountryRepository
import jakarta.inject.Inject

class GetCountryDetailsUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(name: String): Result<CountryDetail> {
        return repository.getCountryDetails(name)
    }
}