package com.wiseowl.woli.domain.usecase.account

import com.wiseowl.woli.domain.repository.AccountRepository

class AccountUseCase(private val accountRepository: AccountRepository) {
    val userState = accountRepository.accountState
    suspend fun createAccount(email: String, password: String, firstName: String, lastName: String) = accountRepository.createAccount(email, password, firstName, lastName)
    suspend fun login(email: String, password: String) = accountRepository.login(email, password)
    suspend fun deleteAccount() = accountRepository.deleteUser()
    suspend fun doesAccountExists(email: String) = accountRepository.isEmailRegistered(email)
    suspend fun addToFavourites(mediaId: Long) = accountRepository.addToFavourites(mediaId)
    suspend fun removeFromFavourites(mediaId: Long) = accountRepository.removeFromFavourites(mediaId)
}