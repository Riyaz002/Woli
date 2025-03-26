package com.wiseowl.woli.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.login.LoginUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.login.model.LoginModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase): PageViewModel<LoginModel>() {

    init {
        _state.update { (Result.Success(LoginModel())) }
    }

    override fun onEvent(action: Action) {
        when(action){
            is LoginEvent.OnEmailChange -> {
                _state.ifSuccess {
                    val isValid = loginUseCase.validateEmail(action.email)
                    val error = if(isValid) null else "Invalid Email"
                    it.copy(email = it.email.copy(value = action.email, error = error))
                }
            }
            is LoginEvent.OnPasswordChange -> {
                _state.ifSuccess {
                    val result = loginUseCase.validatePassword(action.password)
                    val newState = when(result.data){
                        PasswordResult.INVALID_EMPTY_PASSWORD -> it.copy(password = it.password.copy(value = action.password, error = "Password cannot be empty"))
                        PasswordResult.INVALID_NO_UPPERCASE -> it.copy(password = it.password.copy(value = action.password, error = "Password should contain at least one uppercase character letter"))
                        PasswordResult.INVALID_NO_SPECIAL_CHARACTERS -> it.copy(password = it.password.copy(value = action.password, error = "Password should contain at least one special character"))
                        PasswordResult.INVALID_SHORT_PASSWORD -> it.copy(password = it.password.copy(value = action.password, error = "Password should contain at least one special character"))
                        PasswordResult.VALID -> it.copy(password = it.password.copy(value = action.password, error = null))
                    }
                    newState.copy(password = it.password)
                }
            }
            is LoginEvent.OnLoginClick -> {
                (state.value as Result.Success).let {
                    if(it.data.email.valid && it.data.password.valid) {
                        viewModelScope.launch {

//                            loginUseCase.isEmailRegistered(
//                                it.data.email.value, it.data.password.value, it.data.firstName.value, it.data.lastName.value
//                            )
                        }

                    } else{
                        //TODO("tell user that all field must be valid")
                    }
                }
            }
        }
    }
}