package com.capgemini.androidpoc.data.remote.api

import com.capgemini.androidpoc.data.remote.dto.CountryDto
import com.capgemini.androidpoc.domain.model.CountryDetail
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryApi {
    @GET("all")
    suspend fun getAllCountries(): List<CountryDto>

    @GET("name/{name}")
    suspend fun getCountryDetails(@Path("name") name: String): List<CountryDetail>
}