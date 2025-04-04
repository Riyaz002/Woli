package com.wiseowl.woli.ui.screen.profile.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.wiseowl.woli.ui.shared.Constant

@Preview
@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String = "Click Me",
    onClick: () -> Unit = {}
) {
    androidx.compose.material3.Button(onClick = onClick, modifier, shape = RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS)){
        Text(text = text)
    }
}