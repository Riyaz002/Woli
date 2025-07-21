package com.wiseowl.woli.ui.screen.profile

import com.wiseowl.woli.ui.event.Action

sealed class ProfileAction: Action {
    data object DeleteAccountRequest: ProfileAction()
    data object ConfirmDeleteAccount: ProfileAction()
    data object DismissDeleteDialog: ProfileAction()
    data object OnClickLogin: ProfileAction()
}