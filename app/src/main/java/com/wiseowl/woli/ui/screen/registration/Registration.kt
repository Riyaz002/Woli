package com.wiseowl.woli.ui.screen.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.ui.screen.common.Screen
import com.wiseowl.woli.ui.shared.component.BasicButton
import com.wiseowl.woli.ui.shared.component.BasicTextField
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Registration(
    modifier: Modifier = Modifier
) {
    val registrationUseCase by inject<RegistrationUseCase>(RegistrationUseCase::class.java)
    val viewModel = viewModel { RegistrationViewModel(registrationUseCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    Screen(
        modifier.fillMaxSize(),
        data = state.value
    ) {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "Registration",
                style = TextStyle(fontSize = 54.sp)
            )
            Spacer(modifier = Modifier.height(40.dp))
            BasicTextField(
                data = it.firstName,
                onChange = { viewModel.onEvent(RegistrationAction.OnFirstNameChange(it)) },
            )
            BasicTextField(
                data = it.lastName,
                onChange = { viewModel.onEvent(RegistrationAction.OnLastNameChange(it)) },
            )
            BasicTextField(
                data = it.email,
                onChange = { viewModel.onEvent(RegistrationAction.OnEmailChange(it)) },
            )
            BasicTextField(
                data = it.password,
                onChange = { viewModel.onEvent(RegistrationAction.OnPasswordChange(it)) },
            )
            BasicButton(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                data = it.cta,
                actionHandler = viewModel::onEvent
            )
        }
    }
}