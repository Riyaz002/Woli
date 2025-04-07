package com.wiseowl.woli.domain.usecase.profile

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.usecase.account.AccountUseCase

class ProfileUseCase(private val accountUseCase: AccountUseCase) {
    suspend fun getUser(email: String): User{
        return accountUseCase.getUser(email)!!
    }

    suspend fun deleteAccount(email: String){
        accountUseCase.deleteAccount(email)
    }
}