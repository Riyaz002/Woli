package com.wiseowl.woli.ui.screen.category

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun Category(
    modifier: Modifier = Modifier,
    category: String
) {
    Text(text = category)
}