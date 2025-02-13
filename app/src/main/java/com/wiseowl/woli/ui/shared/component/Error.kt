package com.wiseowl.woli.ui.shared.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.ui.screen.detail.component.TextRoundButton

@Composable
fun Error(
    modifier: Modifier = Modifier,
    error: Error
) {
    Column(modifier) {
        Text(
            text = error.reason,
            textAlign = TextAlign.Center,
            fontSize = 72.sp
        )
        Spacer(modifier = Modifier.height(20.dp))

        error.retry?.let {
            TextRoundButton(text = "Retry") { it() }
        }
    }
}