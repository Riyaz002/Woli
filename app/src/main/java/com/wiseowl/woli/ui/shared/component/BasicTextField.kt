package com.wiseowl.woli.ui.shared.component

import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.runtime.rememberUpdatedState
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
    val data = rememberUpdatedState(newValue = data)

    OutlinedTextField(
        modifier = modifier
            .border(
                width = boxStokeWidth.value.dp,
                color = if (data.value.error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface,
                shape = RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS.dp)
            )
            .onFocusChanged { isFocused.value = it.isFocused },
        value = data.value.value,
        onValueChange = {
            data.value.error = null
            onEvent(it)
        },
        placeholder = { Text(text = data.value.label) },
        singleLine = true,
        isError = data.value.error != null,
        shape = RoundedCornerShape(20.dp)
    )
}