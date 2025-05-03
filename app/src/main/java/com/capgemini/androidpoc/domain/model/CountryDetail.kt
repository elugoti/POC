package com.capgemini.androidpoc.domain.model

// data/model/CountryDetail.kt
data class CountryDetail(
    val name: CountryName,
    val capital: List<String>?,
    val region: String,
    val subregion: String?,
    val population: Long,
    val flags: CountryFlags,
    val currencies: Map<String, Currency>?,
    val languages: Map<String, String>?,
    val timezones: List<String>,
    val maps: CountryMaps
)

data class CountryName(
    val common: String,
    val official: String
)

data class CountryFlags(
    val png: String,
    val svg: String,
    val alt: String?
)

data class Currency(
    val name: String,
    val symbol: String?
)

data class CountryMaps(
    val googleMaps: String,
    val openStreetMaps: String
)