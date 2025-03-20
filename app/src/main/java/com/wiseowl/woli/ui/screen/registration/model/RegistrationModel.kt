package com.wiseowl.woli.ui.screen.registration.model

import com.wiseowl.woli.ui.screen.registration.RegistrationEvent
import com.wiseowl.woli.ui.shared.model.Button

data class RegistrationModel(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val password: String = "",
    val cta: Button = Button(
        "Create Account",
        RegistrationEvent.OnRegisterClick
    )
)