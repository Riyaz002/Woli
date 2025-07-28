package com.wiseowl.woli.ui.screen.login

import com.wiseowl.woli.ui.event.Action

sealed class LoginAction: Action {
    data object OnLoginClick : LoginAction()
    data class OnEmailChange(val email: String) : LoginAction()
    data class OnPasswordChange(val password: String) : LoginAction()
}