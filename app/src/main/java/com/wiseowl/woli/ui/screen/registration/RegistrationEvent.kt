package com.wiseowl.woli.ui.screen.registration

import com.wiseowl.woli.domain.event.Action

sealed class RegistrationEvent: Action {
    data object OnRegisterClick : RegistrationEvent()
    data class OnFirstNameChange(val value: String) : RegistrationEvent()
    data class OnLastNameChange(val value: String) : RegistrationEvent()
    data class OnEmailChange(val value: String) : RegistrationEvent()
    data class OnPasswordChange(val value: String) : RegistrationEvent()
}