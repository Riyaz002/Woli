package com.wiseowl.woli.domain.usecase.profile

import com.wiseowl.woli.domain.usecase.account.AccountUseCase

class ProfileUseCase(
    private val accountUseCase: AccountUseCase
) {

    fun getAccountState() = accountUseCase.userState

    suspend fun deleteAccount(){
        accountUseCase.deleteAccount()
    }
}