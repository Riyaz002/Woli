package com.wiseowl.woli.ui.screen.registration

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.usecase.registration.RegistrationUseCase
import com.wiseowl.woli.ui.screen.common.Page
import org.koin.java.KoinJavaComponent.inject

@Composable
fun Registration(
    modifier: Modifier = Modifier
) {
    val registrationUseCase by inject<RegistrationUseCase>(RegistrationUseCase::class.java)
    val viewModel = viewModel { RegistrationViewModel(registrationUseCase) }
    val state = viewModel.state.collectAsStateWithLifecycle()
    Page(modifier, data = state.value) {
        Column {
            TextField(
                value = it.firstName.value,
                onValueChange = { viewModel.onEvent(RegistrationEvent.OnFirstNameChange(it)) },
            )

            TextField(
                value = it.lastName.value,
                onValueChange = { viewModel.onEvent(RegistrationEvent.OnLastNameChange(it)) },
            )
            TextField(
                value = it.email.value,
                onValueChange = { viewModel.onEvent(RegistrationEvent.OnEmailChange(it)) },
            )
            TextField(
                value = it.password.value,
                onValueChange = { viewModel.onEvent(RegistrationEvent.OnPasswordChange(it)) },
            )
        }
    }
}