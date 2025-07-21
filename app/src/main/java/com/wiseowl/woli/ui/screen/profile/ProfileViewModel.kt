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

class ProfileViewModel(private val profileUseCase: ProfileUseCase): ScreenViewModel<ProfileModel>(
    op = {
        val isLoggedIn = profileUseCase.isLoggedIn()
        ProfileModel(isLoggedIn, if(!isLoggedIn) null else profileUseCase.getUserInfo())
    }
) {

    private val _dialogEvent = MutableStateFlow(Event(false))
    val dialogEvent: StateFlow<Event<Boolean>> get() =  _dialogEvent

    override fun onEvent(action: Action) {
        when(action){
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