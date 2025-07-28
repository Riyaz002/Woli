package com.wiseowl.woli.ui.screen.login.model

import com.wiseowl.woli.ui.screen.login.LoginAction
import com.wiseowl.woli.ui.shared.model.Button
import com.wiseowl.woli.ui.shared.model.FieldData

data class LoginModel(
    val email: FieldData = FieldData(label = "Email"),
    val password: FieldData = FieldData(label = "Password"),
    val cta: Button = Button(text =  "Login", action = LoginAction.OnLoginClick)
)