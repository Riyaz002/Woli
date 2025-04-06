package com.wiseowl.woli.ui.screen.registration

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.event.perform
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.registration.model.RegistrationModel
import com.wiseowl.woli.ui.shared.launchWithProgress

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase): PageViewModel<RegistrationModel>(Result.Success(RegistrationModel())) {

    override fun onEvent(action: Action) {
        when(action){
            is RegistrationEvent.OnFirstNameChange -> _state.ifSuccess { it.copy(firstName = it.firstName.copy(value = action.value)) }
            is RegistrationEvent.OnLastNameChange -> _state.ifSuccess { it.copy(lastName = it.lastName.copy(value = action.value)) }
            is RegistrationEvent.OnEmailChange -> {
                _state.ifSuccess { it.copy(email = it.email.copy(value = action.email)) }
                validate("Email") {
                    val isValid = registrationUseCase.validateEmail(action.email)
                    val error = if(isValid) null else "Invalid Email"
                    _state.ifSuccess { current -> current.copy(email = current.email.copy(error = error)) }
                }
            }
            is RegistrationEvent.OnPasswordChange -> {
                _state.ifSuccess {
                    validate("Password") {
                        val result = registrationUseCase.validatePassword(action.password)
                        val error = when(result.data){
                            PasswordResult.INVALID_EMPTY_PASSWORD -> "Password cannot be empty"
                            PasswordResult.INVALID_NO_UPPERCASE -> "Password should contain at least one uppercase character letter"
                            PasswordResult.INVALID_NO_SPECIAL_CHARACTERS -> "Password should contain at least one special character"
                            PasswordResult.INVALID_SHORT_PASSWORD -> "Password should contain at least one special character"
                            PasswordResult.VALID -> null
                        }
                        _state.ifSuccess { current -> current.copy(password = current.password.copy(error = error)) }
                    }
                    it.copy(password = it.password.copy(value = action.password))
                }
            }
            is RegistrationEvent.OnRegisterClick -> {
                (state.value as Result.Success).let {
                    if(it.data.firstName.valid && it.data.lastName.valid && it.data.email.valid && it.data.password.valid) {
                        viewModelScope.launchWithProgress {
                            val result = registrationUseCase.createAccount(
                                it.data.email.value, it.data.password.value, it.data.firstName.value, it.data.lastName.value
                            )
                            if((result as Result.Success).data){
                                onEvent(RegistrationEvent.Login(it.data.email.value, it.data.password.value))
                            }
                            else Action.SnackBar("Oops! something went wrong.").perform()
                        }
                    } else Action.SnackBar("All fields must be valid").perform()
                }
            }
            is RegistrationEvent.Login -> {
                viewModelScope.launchWithProgress {
                    when(val result = registrationUseCase.login(action.email, action.password)){
                        is Result.Success -> Action.Navigate(Screen.HOME).perform()
                        is Result.Error -> ActionHandler.perform(Action.SnackBar(result.error.reason))
                        is Result.Loading -> Unit
                    }
                }
            }
        }
    }
}