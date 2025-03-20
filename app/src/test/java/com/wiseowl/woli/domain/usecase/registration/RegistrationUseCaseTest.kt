package com.wiseowl.woli.domain.usecase.registration

import com.wiseowl.woli.domain.util.Result
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class RegistrationUseCaseTest {
    private lateinit var registrationUseCase: RegistrationUseCase

    private val validEmails = listOf(
        "email@example.com",
        "firstname.lastname@example.com",
        "email@subdomain.example.com",
        "firstname+lastname@example.com",
        "1234567890@example.com",
        "email@example-one.com",
        "_______@example.com",
        "email@example.name",
        "email@example.museum",
        "email@example.co.jp",
        "firstname-lastname@example.com",
    )

    private val invalidEmails = listOf(
        "plainaddress",
        "#@%^%#$@#$@#.com",
        "@example.com",
        "Joe Smith <email@example.com>",
        "email.example.com",
        "email@example@example.com",
        "あいうえお@example.com",
        "email@example.com (Joe Smith)",
        "email@example",
        "email@111.222.333.44444"
    )

    private val validPasswords = listOf(
        "Example@1",
        "Wxamplecom!dd",
    )

    private val invalidPasswords = listOf(
        "Plainaddress",
        "#@%^%#$@#$@#.com",
        "@example.com",
        "",
        "email@111.222.333.44444"
    )

    @Before
    fun setUp() {
        registrationUseCase = RegistrationUseCase()
    }

    @Test
    fun `valid emails is accepted`() {
        validEmails.forEach {
            val isEmailValid = registrationUseCase.validateEmail(it)
            Assert.assertTrue(isEmailValid)
        }
    }

    @Test
    fun `invalid emails are rejected`() {
        invalidEmails.forEach {
            val isEmailValid = registrationUseCase.validateEmail(it)
            Assert.assertFalse(isEmailValid)
        }
    }

    @Test
    fun `valid passwords is accepted`() {
        validPasswords.forEach {
            val result = registrationUseCase.validatePassword(it) as Result.Success
            Assert.assertEquals(result.data, PasswordResult.VALID)
        }
    }

    @Test
    fun `invalid passwords are rejected`() {
        invalidPasswords.forEach {
            val result = registrationUseCase.validatePassword(it) as Result.Success
            Assert.assertNotEquals(result.data, PasswordResult.VALID)
        }
    }
}