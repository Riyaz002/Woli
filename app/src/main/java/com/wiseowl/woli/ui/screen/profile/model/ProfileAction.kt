package com.wiseowl.woli.ui.screen.profile.model

import com.wiseowl.woli.domain.event.Action

sealed class ProfileAction: Action {
    object DeleteAccountRequest: ProfileAction()
    object ConfirmDeleteAccount: ProfileAction()
    object DismissDeleteDialog: ProfileAction()
}