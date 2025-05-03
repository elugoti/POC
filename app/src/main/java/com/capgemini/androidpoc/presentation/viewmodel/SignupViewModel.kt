package com.capgemini.androidpoc.presentation.viewmodel

import android.util.Patterns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.capgemini.androidpoc.utils.isValidEmail

class SignupViewModel : ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var emailError by mutableStateOf<String?>(null)
    var passwordError by mutableStateOf<String?>(null)

    fun onSignupClick(onSuccess: () -> Unit) {
        var isValid = true

        if (email.isValidEmail().not()) {
            emailError = "Invalid email"
            isValid = false
        } else {
            emailError = null
        }

        if (password.length < 6) {
            passwordError = "Password must be at least 6 characters"
            isValid = false
        } else {
            passwordError = null
        }

        if (isValid) onSuccess()
    }
}
