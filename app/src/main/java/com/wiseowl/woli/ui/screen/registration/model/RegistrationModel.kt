package com.wiseowl.woli.ui.screen.registration.model

import com.wiseowl.woli.ui.screen.registration.RegistrationAction
import com.wiseowl.woli.ui.shared.model.Button
import com.wiseowl.woli.ui.shared.model.FieldData

data class RegistrationModel(
    val firstName: FieldData = FieldData(label = "First Name"),
    val lastName: FieldData = FieldData(label = "Last Name"),
    val email: FieldData = FieldData(label = "Email"),
    val password: FieldData = FieldData(label = "Password"),
    val cta: Button = Button(text =  "Create Account", action = RegistrationAction.OnRegisterClick)
)