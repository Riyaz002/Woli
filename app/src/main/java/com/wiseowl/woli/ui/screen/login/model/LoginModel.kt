package com.wiseowl.woli.ui.screen.login.model

import com.wiseowl.woli.ui.shared.model.Button
import com.wiseowl.woli.ui.shared.model.FieldValue

data class LoginModel(
    val email: FieldValue = FieldValue(label = "Email"),
    val password: FieldValue = FieldValue(label = "Password"),
    val cta: Button = Button("Login")
)