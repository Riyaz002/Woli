package com.wiseowl.woli.ui.screen.registration

import com.wiseowl.woli.ui.event.Action

sealed class RegistrationAction: Action {
    data object OnRegisterClick : RegistrationAction()
    data class OnFirstNameChange(val value: String) : RegistrationAction()
    data class OnLastNameChange(val value: String) : RegistrationAction()
    data class OnEmailChange(val email: String) : RegistrationAction()
    data class OnPasswordChange(val password: String) : RegistrationAction()
}