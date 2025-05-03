package com.capgemini.androidpoc.utils

object Routes {
    const val Login = "login"
    const val Signup = "signup"
    const val CountryList = "country_list"
    const val CountryDetail = "country_detail/{name}"

    fun countryDetail(name: String) = "country_detail/$name"
}