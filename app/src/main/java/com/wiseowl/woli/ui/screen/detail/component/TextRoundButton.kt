package com.wiseowl.woli.ui.screen.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun TextRoundButton(
    modifier: Modifier = Modifier,
    radius: Dp = 32.dp,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    text: String,
    textStyle: TextStyle = TextStyle(),
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(radius))
            .background(backgroundColor)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ){
        Text(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
            text = text,
            textAlign = TextAlign.Center,
            color = textColor,
            style = textStyle
        )
    }
}