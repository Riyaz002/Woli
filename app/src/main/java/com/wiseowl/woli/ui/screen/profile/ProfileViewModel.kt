package com.wiseowl.woli.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.event.ReducerBuilder
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.ScreenViewModel
import com.wiseowl.woli.ui.screen.profile.model.ProfileModel
import com.wiseowl.woli.ui.util.Event
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: ProfileUseCase) : ScreenViewModel<ProfileModel>(
    Result.Success(
        ProfileModel(
            isLoggedIn = profileUseCase.getAccountState().value.isLoggedIn,
            currentUser = profileUseCase.getAccountState().value.currentUser
        )
    )
) {


    init {
        viewModelScope.launch {
            profileUseCase.getAccountState().stateIn(
                viewModelScope
            ).collect {
                _state.ifSuccess {
                    ProfileModel(isLoggedIn = it.isLoggedIn, currentUser = it.currentUser)
                }
            }
        }
    }

    private val _dialogEvent = MutableStateFlow(Event(false))
    val dialogEvent: StateFlow<Event<Boolean>> get() = _dialogEvent

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
            on<ProfileAction.OnClickLogin> {
                onEvent(Action.Navigate(Screen.LOGIN))
            }
        }
}