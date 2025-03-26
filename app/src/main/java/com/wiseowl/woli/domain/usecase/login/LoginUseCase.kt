package com.wiseowl.woli.domain.usecase.login

import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.usecase.common.EmailValidator
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.common.PasswordValidator
import com.wiseowl.woli.domain.util.Result

class LoginUseCase(private val accountRepository: AccountRepository) {
    fun validateEmail(email: String): Boolean{
        return EmailValidator().isValidEmail(email)
    }

    fun validatePassword(password: String): Result.Success<PasswordResult> {
        return PasswordValidator().isPasswordValid(password)
    }

    suspend fun isEmailRegistered(email: String): Boolean{
        return accountRepository.isEmailRegistered(email)
    }
}