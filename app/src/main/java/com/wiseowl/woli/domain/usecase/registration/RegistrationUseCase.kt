package com.wiseowl.woli.domain.usecase.registration

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.util.Result

class RegistrationUseCase(private val accountRepository: AccountRepository) {
    fun validateEmail(email: String): Boolean{
        return EmailValidator().isValidEmail(email)
    }

    fun validatePassword(password: String): Result.Success<PasswordResult> {
        return PasswordValidator().isPasswordValid(password)
    }

    suspend fun isEmailRegistered(email: String): Boolean{
        return accountRepository.isEmailRegistered(email)
    }

    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String): Result<Boolean>{
        return accountRepository.createAccount(email, password, firstName, lastName)
    }

    suspend fun deleteAccount(email: String){
        return accountRepository.deleteUser(email)
    }

    suspend fun updateUser(user: User){
        return accountRepository.updateUser(user)
    }

    suspend fun getUser(email: String): User? {
        return accountRepository.getUser(email)
    }
}