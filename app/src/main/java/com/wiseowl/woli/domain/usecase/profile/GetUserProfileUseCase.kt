package com.wiseowl.woli.domain.usecase.profile

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.repository.AccountRepository

class GetUserProfileUseCase(private val accountRepository: AccountRepository) {
    suspend operator fun invoke(email: String): User{
        return accountRepository.getUser(email)!!
    }
}