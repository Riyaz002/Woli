package com.wiseowl.woli.ui.screen.registration

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.registration.PasswordResult
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.registration.model.RegistrationModel
import kotlinx.coroutines.launch

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase): PageViewModel<RegistrationModel>(Result.Success(RegistrationModel())) {

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
                        PasswordResult.INVALID_EMPTY_PASSWORD -> it.copy(password = it.password.copy(value = action.password, error = "Password cannot be empty"))
                        PasswordResult.INVALID_NO_UPPERCASE -> it.copy(password = it.password.copy(value = action.password, error = "Password should contain at least one uppercase character letter"))
                        PasswordResult.INVALID_NO_SPECIAL_CHARACTERS -> it.copy(password = it.password.copy(value = action.password, error = "Password should contain at least one special character"))
                        PasswordResult.INVALID_SHORT_PASSWORD -> it.copy(password = it.password.copy(value = action.password, error = "Password should contain at least one special character"))
                        PasswordResult.VALID -> it.copy(password = it.password.copy(value = action.password, error = null))
                    }
                    newState
                }
            }
            is RegistrationEvent.OnRegisterClick -> {
                (state.value as Result.Success).let {
                    if(it.data.firstName.valid && it.data.lastName.valid && it.data.email.valid && it.data.password.valid) {
                        viewModelScope.launch {
                            registrationUseCase.createAccount(
                                it.data.email.value, it.data.password.value, it.data.firstName.value, it.data.lastName.value
                            )
                        }

                    } else{
                        //TODO("tell user that all field must be valid")
                    }
                }
            }
        }
    }
}