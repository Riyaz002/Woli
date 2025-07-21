package com.wiseowl.woli.ui.screen.profile.model

import com.wiseowl.woli.domain.model.User
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.profile.ProfileAction
import com.wiseowl.woli.ui.shared.model.Button

data class ProfileModel(
    val isLoggedIn: Boolean,
    val currentUser: User?,
    val authenticationButton: Button = Button(
        text = if(currentUser!=null) "Logout" else "Login",
        action = if(currentUser!=null) Action.Logout else ProfileAction.OnClickLogin
    ),
    val privacyPolicyButton: Button = Button(
        text = "Privacy Policy",
        action = Action.Navigate(Screen.PrivacyPolicy)
    ),
    val deleteAccountButton: Button = Button(
        text = "Delete Account",
        action = ProfileAction.DeleteAccountRequest
    )
)