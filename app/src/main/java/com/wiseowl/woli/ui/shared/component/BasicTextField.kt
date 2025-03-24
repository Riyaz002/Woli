package com.wiseowl.woli.ui.shared.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.ui.shared.Constant
import com.wiseowl.woli.ui.shared.model.FieldValue

@Preview
@Composable
fun BasicTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(Constant.DEFAULT_PADDING.dp),
    data: FieldValue = FieldValue(""),
    onEvent: (String) -> Unit = {},
) {
    val isFocused = remember {
        mutableStateOf(false)
    }
    val boxStokeWidth = animateIntAsState(targetValue = if(isFocused.value) 3 else 1, label = "")

    OutlinedTextField(
        modifier = modifier
            .border(
                width = boxStokeWidth.value.dp,
                color = if(data.error!=null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS.dp)
            )
            .onFocusChanged { isFocused.value = it.isFocused },
        value = data.value,
        onValueChange = { onEvent(it) },
        placeholder = { Text(text = data.label) },
        singleLine = true,
        isError = data.error != null,
        shape = RoundedCornerShape(20.dp)
    )
}