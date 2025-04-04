package com.wiseowl.woli.ui.screen.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.ui.screen.common.Page
import com.wiseowl.woli.ui.screen.profile.component.Button

@Preview
@Composable
fun Profile(modifier: Modifier = Modifier) {
    val viewModel = viewModel(modelClass = ProfileViewModel::class)

    Page(modifier = modifier, data = viewModel.state.collectAsStateWithLifecycle().value) { data ->
        Column {
            Button(
                modifier = Modifier.fillMaxWidth(),
                text = "Logout"
            ) { viewModel.onEvent(Action.Logout) }
        }
    }
}