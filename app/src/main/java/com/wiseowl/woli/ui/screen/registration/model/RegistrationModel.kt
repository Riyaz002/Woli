package com.wiseowl.woli.ui.screen.registration.model

import com.wiseowl.woli.ui.shared.model.Button
import com.wiseowl.woli.ui.shared.model.FieldValue

data class RegistrationModel(
    val firstName: FieldValue = FieldValue(label = "First Name"),
    val lastName: FieldValue = FieldValue(label = "Last Name"),
    val email: FieldValue = FieldValue(label = "Email"),
    val password: FieldValue = FieldValue(label = "Password"),
    val cta: Button = Button("Create Account")
)