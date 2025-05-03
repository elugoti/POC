package com.capgemini.androidpoc.presentation.viewmodel

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignupViewModelTest {

    private lateinit var viewModel: SignupViewModel

    @Before
    fun setUp() {
        viewModel = SignupViewModel()
    }

    @Test
    fun `onSignupClick with valid email and password should call onSuccess`() {
        var called = false
        viewModel.email = "test@example.com"
        viewModel.password = "123456"

        viewModel.onSignupClick { called = true }

        assertTrue(called)
        assertNull(viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `onSignupClick with invalid email should set email error`() {
        viewModel.email = "invalid-email"
        viewModel.password = "123456"

        viewModel.onSignupClick {}

        assertEquals("Invalid email", viewModel.emailError)
        assertNull(viewModel.passwordError)
    }

    @Test
    fun `onSignupClick with short password should set password error`() {
        viewModel.email = "test@example.com"
        viewModel.password = "123"

        viewModel.onSignupClick {}

        assertEquals("Password must be at least 6 characters", viewModel.passwordError)
        assertNull(viewModel.emailError)
    }

    @Test
    fun `onSignupClick with invalid email and short password should set both errors`() {
        viewModel.email = "invalid"
        viewModel.password = "123"

        viewModel.onSignupClick {}

        assertEquals("Invalid email", viewModel.emailError)
        assertEquals("Password must be at least 6 characters", viewModel.passwordError)
    }
}
