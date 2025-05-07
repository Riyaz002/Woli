package com.wiseowl.woli.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.profile.ProfileUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.profile.model.ProfileAction
import com.wiseowl.woli.ui.screen.profile.model.ProfileModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: ProfileUseCase): PageViewModel<ProfileModel>() {

    init {
        viewModelScope.launch {
            _state.update {
                Result.Success(ProfileModel(profileUseCase.getUserInfo()))
            }
        }
    }

    override fun onEvent(action: Action) {
        when(action){
            ProfileAction.DeleteAccount -> {
                viewModelScope.launch {
                    profileUseCase.deleteAccount()
                    onEvent(Action.Navigate(Screen.LOGIN))
                }
            }
            else -> ActionHandler.perform(action)
        }
    }
}