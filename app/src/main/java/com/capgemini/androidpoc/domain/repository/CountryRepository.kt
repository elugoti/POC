package com.capgemini.androidpoc.domain.repository

import com.capgemini.androidpoc.domain.model.Country
import com.capgemini.androidpoc.domain.model.CountryDetail

interface CountryRepository {
    suspend fun getCountries(): List<Country>
    suspend fun getCountryDetails(name: String): Result<CountryDetail>
}