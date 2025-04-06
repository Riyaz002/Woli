package com.wiseowl.woli.ui.screen.profile

import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.usecase.profile.GetUserProfileUseCase
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.profile.model.ProfileModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileUseCase: GetUserProfileUseCase): PageViewModel<ProfileModel>() {

    init {
        viewModelScope.launch {
            _state.update {
                Result.Success(ProfileModel(profileUseCase.invoke(Firebase.auth.currentUser?.email!!)))
            }
        }
    }

    override fun onEvent(action: Action) {
        when(action){
            else -> ActionHandler.perform(action)
        }
    }
}