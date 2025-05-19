package com.wiseowl.woli.ui.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.login.LoginUseCase
import com.wiseowl.woli.ui.navigation.Screen
import com.wiseowl.woli.ui.screen.common.Page
import com.wiseowl.woli.ui.shared.component.BasicButton
import com.wiseowl.woli.ui.shared.component.BasicTextField
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Login(
    modifier: Modifier = Modifier
) {
    val registrationUseCase: LoginUseCase by inject(LoginUseCase::class.java)
    val viewModel = viewModel { LoginViewModel(registrationUseCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    Page(
        modifier.fillMaxSize(),
        data = state.value
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Login",
                style = TextStyle(fontSize = 54.sp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            BasicTextField(
                data = it.email,
                onChange = { viewModel.onEvent(LoginAction.OnEmailChange(it)) },
            )
            BasicTextField(
                data = it.password,
                onChange = { viewModel.onEvent(LoginAction.OnPasswordChange(it)) },
            )
            BasicButton(
                data = it.cta,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { viewModel.onEvent(LoginAction.OnLoginClick) }
            )
            TextButton(modifier = Modifier.padding(top = 10.dp).align(Alignment.CenterHorizontally), onClick = {viewModel.onEvent(Action.Navigate(Screen.REGISTRATION))}){
                Text(text = "Don't have an account? Register", textDecoration = TextDecoration.Underline)
            }
        }
    }
}