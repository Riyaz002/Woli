package com.wiseowl.woli.ui.screen.profile

import com.wiseowl.woli.ui.event.Action

sealed class ProfileAction: Action {
    object DeleteAccountRequest: ProfileAction()
    object ConfirmDeleteAccount: ProfileAction()
    object DismissDeleteDialog: ProfileAction()
}