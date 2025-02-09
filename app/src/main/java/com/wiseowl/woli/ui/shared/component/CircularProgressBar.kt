package com.wiseowl.woli.ui.shared.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.background(Color.DarkGray.copy(0.5f)).clickable(false){},
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0f)
        )
    }
}