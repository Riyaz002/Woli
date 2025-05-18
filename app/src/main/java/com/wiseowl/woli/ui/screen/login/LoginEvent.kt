package com.wiseowl.woli.ui.screen.login

import com.wiseowl.woli.ui.event.Action

sealed class LoginEvent: Action {
    data object OnLoginClick : LoginEvent()
    data class OnEmailChange(val email: String) : LoginEvent()
    data class OnPasswordChange(val password: String) : LoginEvent()
}