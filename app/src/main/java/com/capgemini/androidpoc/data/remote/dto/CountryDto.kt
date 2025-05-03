package com.capgemini.androidpoc.data.remote.dto

import com.capgemini.androidpoc.domain.model.Country

data class CountryDto(
    val name: Name
) {
    data class Name(val common: String)

    fun toDomain(): Country {
        return Country(name.common)
    }
}