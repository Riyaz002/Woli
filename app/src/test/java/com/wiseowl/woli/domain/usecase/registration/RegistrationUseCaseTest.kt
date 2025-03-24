package com.wiseowl.woli.domain.usecase.registration

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.TestAccountRepository
import com.wiseowl.woli.domain.util.Result
import kotlinx.coroutines.runBlocking
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
        registrationUseCase = RegistrationUseCase(TestAccountRepository())
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

    @Test
    fun `isEmailRegistered return true if user with email exists`() = runBlocking{
        val email = "email@example.com"
        val user = User(
            firstName = "Riyaz",
            lastName = "Uddin",
            uid = "acoimcsomecem",
            email = email,
            favourites = null
        )
        registrationUseCase.createAccount(user.email, "RANDOM", user.firstName, user.lastName)
        val isEmailRegistered = registrationUseCase.isEmailRegistered(email)

        Assert.assertTrue(isEmailRegistered)
    }

    @Test
    fun `deleteUser deletes user with email`() = runBlocking{
        val email = "email@example.com"
        val user = User(
            firstName = "Riyaz",
            lastName = "Uddin",
            uid = "acoimcsomecem",
            email = email,
            favourites = null
        )
        registrationUseCase.createAccount(user.email, "RANDOM", user.firstName, user.lastName)
        val isEmailRegistered = registrationUseCase.isEmailRegistered(email)
        registrationUseCase.deleteAccount(email)
        val userDeleted = registrationUseCase.isEmailRegistered(email)
        Assert.assertTrue(isEmailRegistered)
        Assert.assertFalse(userDeleted)
    }

    @Test
    fun `updateUser updates user with the email`() = runBlocking{
        val email = "email@example.com"
        val user = User(
            firstName = "Riyaz",
            lastName = "Uddin",
            uid = "acoimcsomecem",
            email = email,
            favourites = null
        )
        registrationUseCase.createAccount(user.email, "RANDOM", user.firstName, user.lastName)
        val isEmailRegistered = registrationUseCase.isEmailRegistered(email)
        registrationUseCase.deleteAccount(email)
        val userDeleted = registrationUseCase.isEmailRegistered(email)
        Assert.assertTrue(isEmailRegistered)
        Assert.assertFalse(userDeleted)
    }
}