package com.wiseowl.woli.ui.screen.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.event.ActionHandler
import com.wiseowl.woli.domain.util.Result
import com.wiseowl.woli.ui.shared.component.Error

@Composable
fun <T> Page(
    modifier: Modifier = Modifier,
    data: Result<T>,
    navigationBarVisible: Boolean = true,
    content: @Composable BoxScope.(T) -> Unit,
) {
    LaunchedEffect(key1 = navigationBarVisible) {
        ActionHandler.perform(Action.NavigationBarVisible(navigationBarVisible))
    }
    when(data){
        is Result.Error -> Error(modifier = modifier, error = data.error)
        is Result.Loading -> ActionHandler.perform(Action.Progress(true))
        is Result.Success -> {
            ActionHandler.perform(Action.Progress(false))
            Box(modifier = modifier){
                content(data.data)
            }
        }
    }
}