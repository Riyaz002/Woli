package com.wiseowl.woli.ui.screen.registration

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.Action.*
import com.wiseowl.woli.ui.event.perform
import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.domain.usecase.common.PasswordResult
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.registration.model.RegistrationModel
import com.wiseowl.woli.ui.shared.launchWithProgress
import java.util.Timer
import java.util.TimerTask

class RegistrationViewModel(private val registrationUseCase: RegistrationUseCase): ScreenViewModel<RegistrationModel>(Result.Success(RegistrationModel())) {

    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<RegistrationAction.OnFirstNameChange> { action ->
                _state.ifSuccess { it.copy(firstName = it.firstName.copy(value = action.value)) }
                on<RegistrationAction.OnLastNameChange> { action ->
                    _state.ifSuccess { it.copy(lastName = it.lastName.copy(value = action.value)) }
                    on<RegistrationAction.OnEmailChange> { action ->
                        _state.ifSuccess { it.copy(email = it.email.copy(value = action.email)) }
                        validate("Email") {
                            val isValid = registrationUseCase.validateEmail(action.email)
                            val error = if (isValid) null else "Invalid Email"
                            _state.ifSuccess { current ->
                                current.copy(
                                    email = current.email.copy(
                                        error = error
                                    )
                                )
                            }
                        }
                    }
                    on<RegistrationAction.OnPasswordChange> { action ->
                        _state.ifSuccess {
                            validate("Password") {
                                val result = registrationUseCase.validatePassword(action.password)
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
                    on<RegistrationAction.OnRegisterClick> {
                        (state.value as Result.Success).let {
                            if (it.data.firstName.valid && it.data.lastName.valid && it.data.email.valid && it.data.password.valid) {
                                viewModelScope.launchWithProgress {
                                    val result = registrationUseCase.createAccount(
                                        it.data.email.value,
                                        it.data.password.value,
                                        it.data.firstName.value,
                                        it.data.lastName.value
                                    )
                                    when (result) {
                                        is Result.Success<User> -> Navigate(Screen.HOME).perform()
                                        is Result.Error<*> -> SnackBar(result.error.reason).perform()
                                        else -> Unit
                                    }
                                }
                            } else SnackBar("All fields must be valid").perform()
                        }
                    }
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