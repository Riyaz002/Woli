package com.wiseowl.woli.ui.screen.profile.component

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.ui.configuration.Constant

@Preview
@Composable
fun Button(
    modifier: Modifier = Modifier,
    text: String = "Click Me",
    onClick: () -> Unit = {}
) {
    androidx.compose.material3.Button(
        onClick = onClick,
        modifier
            .height(70.dp)
            .padding(horizontal = 10.dp),
        shape = RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS)
    ) {
        Text(
            text = text,
            fontSize = 18.sp
        )
    }
}