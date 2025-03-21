package com.wiseowl.woli.ui.screen.registration

import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.repository.AccountRepository
import com.wiseowl.woli.domain.usecase.registration.PasswordResult
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.registration.model.RegistrationModel
import kotlinx.coroutines.flow.update

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase): PageViewModel<RegistrationModel>() {

    init {
        _state.update { (Result.Success(RegistrationModel())) }
    }

    override fun onEvent(action: Action) {
        when(action){
            is RegistrationEvent.OnFirstNameChange -> _state.ifSuccess { it.copy(firstName = it.firstName.copy(value = action.value)) }
            is RegistrationEvent.OnLastNameChange -> _state.ifSuccess { it.copy(lastName = it.lastName.copy(value = action.value)) }
            is RegistrationEvent.OnEmailChange -> {
                _state.ifSuccess {
                    val isValid = registrationUseCase.validateEmail(action.email)
                    val error = if(isValid) null else "Invalid Email"
                    it.copy(email = it.email.copy(value = action.email, error = error))
                }
            }
            is RegistrationEvent.OnPasswordChange -> {
                _state.ifSuccess {
                    val result = registrationUseCase.validatePassword(action.password)
                    val newState = when(result.data){
                        PasswordResult.INVALID_EMPTY_PASSWORD -> it.copy(password = it.password.copy(error = "Password cannot be empty"))
                        PasswordResult.INVALID_NO_UPPERCASE -> it.copy(password = it.password.copy(error = "Password should contain at least one uppercase character letter"))
                        PasswordResult.INVALID_NO_SPECIAL_CHARACTERS -> it.copy(password = it.password.copy(error = "Password should contain at least one special character"))
                        PasswordResult.INVALID_SHORT_PASSWORD -> it.copy(password = it.password.copy(error = "Password should contain at least one special character"))
                        PasswordResult.VALID -> it.copy(password = it.password.copy(error = null))
                    }
                    newState.copy(password = it.password)
                }
            }
            is RegistrationEvent.OnRegisterClick -> {

            }
        }
    }
}