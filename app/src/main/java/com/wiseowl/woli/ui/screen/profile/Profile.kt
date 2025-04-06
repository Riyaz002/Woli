package com.wiseowl.woli.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.profile.GetUserProfileUseCase
import com.wiseowl.woli.ui.screen.common.Page
import com.wiseowl.woli.ui.screen.profile.component.Button
import org.koin.java.KoinJavaComponent.inject

@Preview
@Composable
fun Profile(modifier: Modifier = Modifier) {
    val getUserProfileUseCase by inject<GetUserProfileUseCase>(GetUserProfileUseCase::class.java)
    val viewModel = viewModel{ ProfileViewModel(getUserProfileUseCase) }

    Page(modifier = modifier.padding(
        top = 30.dp
    ), data = viewModel.state.collectAsStateWithLifecycle().value) { data ->
        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                text = "Logout"
            ) { viewModel.onEvent(Action.Logout) }
        }
    }
}