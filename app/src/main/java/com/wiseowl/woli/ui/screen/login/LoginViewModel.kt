package com.wiseowl.woli.ui.screen.login

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.login.LoginUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.Action.Navigate
import com.wiseowl.woli.ui.event.Action.SnackBar
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.event.perform
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.login.model.LoginModel
import com.wiseowl.woli.ui.shared.launchWithProgress
import java.util.Timer
import java.util.TimerTask

class LoginViewModel(private val loginUseCase: LoginUseCase): ScreenViewModel<LoginModel>(Result.Success(LoginModel())) {

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<LoginAction.OnEmailChange> { action ->
                _state.ifSuccess { it.copy(email = it.email.copy(value = action.email)) }
                validate("Email") {
                    val isValid = loginUseCase.validateEmail(action.email)
                    val error = if (isValid) null else "Invalid Email"
                    _state.ifSuccess { current -> current.copy(email = current.email.copy(error = error)) }
                }
            }
            on<LoginAction.OnPasswordChange> { action ->
                _state.ifSuccess {
                    validate("Password") {
                        val result = loginUseCase.validatePassword(action.password)
                        val error = when (result.data) {
                            PasswordResult.INVALID_EMPTY_PASSWORD -> "Password cannot be empty"
                            PasswordResult.INVALID_NO_UPPERCASE -> "Password should contain at least one uppercase character letter"
                            PasswordResult.INVALID_NO_SPECIAL_CHARACTERS -> "Password should contain at least one special character"
                            PasswordResult.INVALID_SHORT_PASSWORD -> "Password should contain at least one special character"
                            PasswordResult.VALID -> null
                        }
                        _state.ifSuccess { current ->
                            current.copy(
                                password = current.password.copy(
                                    error = error
                                )
                            )
                        }
                    }
                    it.copy(password = it.password.copy(value = action.password))
                }
            }
            on<LoginAction.OnLoginClick> {
                (state.value as Result.Success).let {
                    if (it.data.email.valid && it.data.password.valid) {
                        viewModelScope.launchWithProgress {
                            val result = loginUseCase.isEmailRegistered(it.data.email.value)
                            if (result) {
                                val loginResult =
                                    loginUseCase.login(it.data.email.value, it.data.password.value)
                                when (loginResult) {
                                    is Result.Success -> Navigate(Screen.HOME).perform()
                                    else -> SnackBar("Password or Email is incorrect").perform()
                                }
                            } else SnackBar("This email is not registered").perform()
                        }
                    } else SnackBar("All fields must be valid").perform()
                }
            }
        }

    private val timers: HashMap<String, Timer> = hashMapOf()

    /**
     * Perform [action] with some delay.
     * The [label] is unique identifier for the [action].
     * If the function is called with the same [label] while the previous one hasn't been executed, the previous [action] will be canceled.
     */
    fun validate(label: String, action: () -> Unit){
        timers[label]?.cancel()
        val newTimer = Timer()
        timers[label] = newTimer
        newTimer.schedule(
            object : TimerTask() {
                override fun run() {
                    action()
                }
            }, 1000
        )
    }

    override fun onCleared() {
        super.onCleared()
        timers.forEach { it.value.cancel() }
        timers.clear()
    }
}