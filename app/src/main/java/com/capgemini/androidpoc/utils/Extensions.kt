package com.capgemini.androidpoc.utils

 fun String.isValidEmail(): Boolean {
    // Simple but effective regex for email validation in tests
    return Regex("^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})").matches(this)
}