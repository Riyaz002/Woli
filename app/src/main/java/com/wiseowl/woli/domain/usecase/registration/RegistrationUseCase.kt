package com.wiseowl.woli.domain.usecase.registration

import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result

class RegistrationUseCase(private val accountRepository: AccountRepository) {
    fun validateEmail(email: String): Boolean{
        return EmailValidator().isValidEmail(email)
    }

    fun validatePassword(password: String): Result<PasswordResult> {
        return PasswordValidator().isPasswordValid(password)
    }

    suspend fun isEmailRegistered(email: String): Boolean{
        return accountRepository.isEmailRegistered(email)
    }
}