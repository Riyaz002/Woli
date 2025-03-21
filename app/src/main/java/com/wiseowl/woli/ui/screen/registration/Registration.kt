package com.wiseowl.woli.ui.screen.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.ui.screen.common.Page
import com.wiseowl.woli.ui.shared.component.BasicTextField
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Registration(
    modifier: Modifier = Modifier
) {
    val registrationUseCase by inject<RegistrationUseCase>(RegistrationUseCase::class.java)
    val viewModel = viewModel { RegistrationViewModel(registrationUseCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    Page(modifier, data = state.value) {
        Column(modifier) {
            Spacer(modifier = Modifier.height(40.dp))
            BasicTextField(
                data = it.firstName,
                onEvent = { viewModel.onEvent(RegistrationEvent.OnFirstNameChange(it)) },
            )
            BasicTextField(
                data = it.lastName,
                onEvent = { viewModel.onEvent(RegistrationEvent.OnLastNameChange(it)) },
            )
            BasicTextField(
                data = it.email,
                onEvent = { viewModel.onEvent(RegistrationEvent.OnEmailChange(it)) },
            )
            BasicTextField(
                data = it.password,
                onEvent = { viewModel.onEvent(RegistrationEvent.OnPasswordChange(it)) },
            )
        }
    }
}