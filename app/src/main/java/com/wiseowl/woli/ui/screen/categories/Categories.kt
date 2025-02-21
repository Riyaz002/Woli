package com.wiseowl.woli.ui.screen.categories

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.ui.screen.common.Page

@Composable
fun Categories(
    modifier: Modifier = Modifier
) {
    val viewModel = viewModel(modelClass = CategoriesViewModel::class)
    val state = viewModel.state.collectAsStateWithLifecycle()

    Page(
        modifier = modifier,
        data = state.value
    ) {

    }
}