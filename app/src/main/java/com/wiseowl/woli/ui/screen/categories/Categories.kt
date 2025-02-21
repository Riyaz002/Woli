package com.wiseowl.woli.ui.screen.categories

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.wiseowl.woli.ui.screen.categories.component.Category
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
        Text(
            text = "Categories",
            fontSize = 32.sp,
            fontWeight = FontWeight.SemiBold
        )

        LazyColumn {
            items(it.categories.orEmpty()){ category ->
                Category(category = category)
            }
        }
    }
}