package com.wiseowl.woli.ui.screen.registration

import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.registration.model.RegistrationModel

class RegistrationViewModel: PageViewModel<RegistrationModel>() {
    override fun onEvent(action: Action) {
        when(action){
            is RegistrationEvent.OnFirstNameChange -> _state.ifSuccess { it.copy(firstName = action.value) }
            is RegistrationEvent.OnLastNameChange -> _state.ifSuccess { it.copy(lastName = action.value) }
            is RegistrationEvent.OnEmailChange -> _state.ifSuccess { it.copy(email = action.value) }
            is RegistrationEvent.OnPasswordChange -> _state.ifSuccess { it.copy(password = action.value) }
            is RegistrationEvent.OnRegisterClick -> {

            }
        }
    }
}