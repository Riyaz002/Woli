package com.wiseowl.woli.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.profile.model.ProfileModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.wiseowl.woli.ui.util.Event

class ProfileViewModel(private val profileUseCase: ProfileUseCase): ScreenViewModel<ProfileModel>() {

    private val _dialogEvent = MutableStateFlow(Event<Boolean>(false))
    val dialogEvent: StateFlow<Event<Boolean>> get() =  _dialogEvent

    init {
        viewModelScope.launch {
            _state.update {
                Result.Success(ProfileModel(profileUseCase.getUserInfo()))
            }
        }
    }


    override val actionReducer: ReducerBuilder.() -> Unit
        get() = {
            on<ProfileAction.DeleteAccountRequest> {
                viewModelScope.launch {
                    _dialogEvent.update { Event(true) }
                }
            }
            on<ProfileAction.ConfirmDeleteAccount> {
                viewModelScope.launch {
                    profileUseCase.deleteAccount()
                    onEvent(Action.Navigate(Screen.LOGIN))
                }
            }
            on<ProfileAction.DismissDeleteDialog> {
                viewModelScope.launch {
                    _dialogEvent.update { Event(false) }
                }
            }
        }
}