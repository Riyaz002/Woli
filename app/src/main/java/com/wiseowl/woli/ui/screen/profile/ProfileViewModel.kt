package com.wiseowl.woli.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.event.ActionHandler
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.profile.model.ProfileModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.wiseowl.woli.ui.util.Event
import com.wiseowl.woli.util.Logger
import kotlinx.coroutines.flow.stateIn

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ScreenViewModel<ProfileModel>(
    Result.Success(
        ProfileModel(
            isLoggedIn = false,
            currentUser = null
        )
    )
) {


    init {
        viewModelScope.launch {
            profileUseCase.getAccountState().stateIn(
                viewModelScope
            ).collect {
                Logger.e(it.toString())
                _state.ifSuccess { state ->
                    ProfileModel(isLoggedIn = it.isLoggedIn, currentUser = it.currentUser)
                }
            }
        }
    }

    private val _dialogEvent = MutableStateFlow(Event(false))
    val dialogEvent: StateFlow<Event<Boolean>> get() = _dialogEvent

    override fun onEvent(action: Action) {
        when (action) {
            ProfileAction.DeleteAccountRequest -> {
                viewModelScope.launch {
                    _dialogEvent.update { Event(true) }
                }
            }

            ProfileAction.ConfirmDeleteAccount -> {
                viewModelScope.launch {
                    profileUseCase.deleteAccount()
                    onEvent(Action.Navigate(Screen.LOGIN))
                }
            }

            ProfileAction.DismissDeleteDialog -> {
                viewModelScope.launch {
                    _dialogEvent.update { Event(false) }
                }
            }

            ProfileAction.OnClickLogin -> onEvent(Action.Navigate(Screen.LOGIN))
            else -> ActionHandler.perform(action)
        }
    }
}