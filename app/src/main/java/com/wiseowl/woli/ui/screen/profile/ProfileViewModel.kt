package com.wiseowl.woli.ui.screen.profile

import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.ui.screen.common.PageViewModel
import com.wiseowl.woli.ui.screen.profile.model.ProfileModel

class ProfileViewModel: PageViewModel<ProfileModel>() {

    override fun onEvent(action: Action) {
        when(action){
            else -> ActionHandler.perform(action)
        }
    }
}