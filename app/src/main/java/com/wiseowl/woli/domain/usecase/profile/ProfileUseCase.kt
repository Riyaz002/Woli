package com.wiseowl.woli.domain.usecase.profile

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.usecase.account.AccountUseCase

class ProfileUseCase(private val accountUseCase: AccountUseCase) {
    suspend fun getUserInfo(): User{
        return accountUseCase.getUserInfo()!!
    }

    suspend fun deleteAccount(){
        accountUseCase.deleteAccount()
    }
}