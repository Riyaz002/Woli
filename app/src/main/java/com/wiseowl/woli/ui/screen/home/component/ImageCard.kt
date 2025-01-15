package com.wiseowl.woli.ui.screen.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    url: String,
    description: String
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = url,
            contentDescription = description
        )
    }
}