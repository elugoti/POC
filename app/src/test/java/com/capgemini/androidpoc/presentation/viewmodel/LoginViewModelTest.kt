package com.capgemini.androidpoc.presentation.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `onLoginClick with valid credentials should call onSuccess`() {
        var called = false
        viewModel.email = "test@example.com"
        viewModel.password = "123456"

        viewModel.onLoginClick { called = true }

        assertTrue(called)
        assertNull(viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `onLoginClick with invalid email should set email error`() {
        viewModel.email = "invalid-email"
        viewModel.password = "123456"

        viewModel.onLoginClick {}

        assertEquals("Invalid email", viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `onLoginClick with short password should set password error`() {
        viewModel.email = "test@example.com"
        viewModel.password = "123"

        viewModel.onLoginClick {}

        assertEquals("Password must be at least 6 characters", viewModel.passwordError)
        assertNull(viewModel.emailError)
    }

    @Test
    fun `onLoginClick with both invalid should set both errors`() {
        viewModel.email = "invalid"
        viewModel.password = "123"

        viewModel.onLoginClick {}

        assertEquals("Invalid email", viewModel.emailError)
        assertEquals("Password must be at least 6 characters", viewModel.passwordError)
    }
}
