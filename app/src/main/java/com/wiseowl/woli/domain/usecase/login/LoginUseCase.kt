package com.wiseowl.woli.domain.usecase.login

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.EmailValidator
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.common.PasswordValidator
import com.wiseowl.woli.domain.util.Result

class LoginUseCase(
    private val accountUseCase: AccountUseCase
) {
    fun validateEmail(email: String): Boolean{
        return EmailValidator().isValidEmail(email)
    }

    fun validatePassword(password: String): Result.Success<PasswordResult> {
        return PasswordValidator().isPasswordValid(password)
    }

    suspend fun isEmailRegistered(email: String): Boolean{
        return accountUseCase.doesAccountExists(email)
    }

    suspend fun login(email: String, password: String): Result<User> {
        return accountUseCase.login(email, password)
    }
}