package com.wiseowl.woli.ui.shared.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wiseowl.woli.domain.model.Error
import com.wiseowl.woli.ui.screen.detail.component.TextRoundButton

@Composable
fun Error(
    modifier: Modifier = Modifier,
    error: Error,
    retry: (() -> Unit)? = null
) {
    Box(
        modifier = modifier
            .requiredWidth(LocalConfiguration.current.screenWidthDp.dp)
            .requiredHeight(LocalConfiguration.current.screenHeightDp.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = error.reason,
                textAlign = TextAlign.Center,
                fontSize = 54.sp,
                style = TextStyle(lineHeight = 72.sp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            retry?.let {
                TextRoundButton(
                    text = "Retry",
                    textStyle = TextStyle(
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Medium
                    )
                ) { retry() }
            }
        }
    }
}