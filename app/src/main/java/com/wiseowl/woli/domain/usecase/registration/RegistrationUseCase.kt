package com.wiseowl.woli.domain.usecase.registration

import com.wiseowl.woli.domain.repository.UserRepository
import com.wiseowl.woli.domain.util.Result

class RegistrationUseCase(user: UserRepository) {
    fun validateEmail(email: String): Boolean{
        return EmailValidator().isValidEmail(email)
    }

    fun validatePassword(password: String): Result<PasswordResult> {
        return PasswordValidator().isPasswordValid(password)
    }

    fun isEmailRegistered(email: String): Boolean{
        //TODO: Implement
        return false
    }
}