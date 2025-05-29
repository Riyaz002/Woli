package com.wiseowl.woli.domain.usecase.account

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository

class AccountUseCase(private val accountRepository: AccountRepository) {
    fun isLoggedIn(): Boolean = accountRepository.isLoggedIn()
    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String) = accountRepository.createAccount(email, password, firstName, lastName)
    suspend fun login(email: String, password: String) = accountRepository.login(email, password)
    suspend fun deleteAccount() = accountRepository.deleteUser()
    suspend fun updateUser(user: User) = accountRepository.updateUser(user)
    suspend fun doesAccountExists(email: String) = accountRepository.isEmailRegistered(email)
    suspend fun getUserInfo() = accountRepository.getUserInfo()
}