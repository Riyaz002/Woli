package com.wiseowl.woli.ui.screen.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextRoundButton(
    modifier: Modifier = Modifier,
    radius: Dp = 32.dp,
    color: Color = MaterialTheme.colorScheme.primary,
    text: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier

            .background(color = color, shape = RoundedCornerShape(radius))
            .clickable { onClick() },
    ){
        Text(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            text = text
        )
    }
}