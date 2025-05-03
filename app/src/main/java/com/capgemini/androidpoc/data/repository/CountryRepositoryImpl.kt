package com.capgemini.androidpoc.data.repository

import com.capgemini.androidpoc.data.remote.api.CountryApi
import com.capgemini.androidpoc.domain.model.Country
import com.capgemini.androidpoc.domain.model.CountryDetail
import com.capgemini.androidpoc.domain.repository.CountryRepository
import jakarta.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val api: CountryApi
) : CountryRepository {
    override suspend fun getCountries(): List<Country> {
        return api.getAllCountries().map { it.toDomain() }
    }

    override suspend fun getCountryDetails(name: String): Result<CountryDetail> {
        return try {
            val response = api.getCountryDetails(name)
            if (response.isNotEmpty()) {
                Result.success(response[0])
            } else {
                Result.failure(Exception("Country not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}