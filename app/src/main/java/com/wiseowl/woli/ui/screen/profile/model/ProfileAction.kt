package com.wiseowl.woli.ui.screen.profile.model

import com.wiseowl.woli.domain.event.Action

sealed class ProfileAction: Action {
    object DeleteAccount: ProfileAction()
}