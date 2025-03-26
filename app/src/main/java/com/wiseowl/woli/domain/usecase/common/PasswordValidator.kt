package com.wiseowl.woli.domain.usecase.common

import com.wiseowl.woli.domain.util.Result


const val  MIN_PASSWORD_LENGTH = 8

class PasswordValidator {
    fun isPasswordValid(password: String): Result.Success<PasswordResult> {
        if(password.isEmpty()) Result.Success(PasswordResult.INVALID_EMPTY_PASSWORD)
        if(password.length < MIN_PASSWORD_LENGTH) return Result.Success(PasswordResult.INVALID_SHORT_PASSWORD)
        val passwordRegexCapitalChar = Regex("^(?=.*[A-Z]).+\$")
        val passwordRegexSpecialChar = Regex("^(?=.*[\\W_]).+\$")
        if(!password.contains(passwordRegexCapitalChar)) return Result.Success(PasswordResult.INVALID_NO_UPPERCASE)
        if(!password.contains(passwordRegexSpecialChar)) return Result.Success(PasswordResult.INVALID_NO_SPECIAL_CHARACTERS)
        return Result.Success(PasswordResult.VALID)
    }
}

enum class PasswordResult{
    INVALID_EMPTY_PASSWORD,
    INVALID_SHORT_PASSWORD,
    INVALID_NO_UPPERCASE,
    INVALID_NO_SPECIAL_CHARACTERS,
    VALID
}