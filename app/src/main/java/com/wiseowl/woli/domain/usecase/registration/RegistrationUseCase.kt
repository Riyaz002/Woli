package com.wiseowl.woli.domain.usecase.registration

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.usecase.account.AccountUseCase
import com.wiseowl.woli.domain.usecase.common.EmailValidator
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.common.PasswordValidator
import com.wiseowl.woli.domain.util.Result

class RegistrationUseCase(private val accountUseCase: AccountUseCase) {
    fun validateEmail(email: String): Boolean{
        return EmailValidator().isValidEmail(email)
    }

    fun validatePassword(password: String): Result.Success<PasswordResult> {
        return PasswordValidator().isPasswordValid(password)
    }

    suspend fun isEmailRegistered(email: String): Boolean{
        return accountUseCase.doesAccountExists(email)
    }

    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String): Result<Boolean>{
        return accountUseCase.createAccount(email, password, firstName, lastName)
    }

    suspend fun login(email: String, password: String): Result<User> {
        return accountUseCase.login(email, password)
    }

    suspend fun deleteAccount(){
        return accountUseCase.deleteAccount()
    }
}