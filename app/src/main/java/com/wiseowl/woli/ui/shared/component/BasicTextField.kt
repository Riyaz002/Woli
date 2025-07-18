package com.wiseowl.woli.ui.shared.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.ui.configuration.Constant
import com.wiseowl.woli.ui.shared.component.modifier.glowingBorder
import com.wiseowl.woli.ui.shared.model.FieldData

@Preview
@Composable
fun BasicTextField(
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .padding(Constant.DEFAULT_PADDING.dp),
    data: FieldData = FieldData(""),
    onChange: (String) -> Unit = {},
    trailingIcon: ImageVector? = null,
    onTrailingIconClick: () -> Unit = {},
) {
    val isFocused = remember { mutableStateOf(false) }
    val data = rememberUpdatedState(newValue = data)
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier
            .glowingBorder(
                10,
                color = if (data.value.error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                isGlowing = isFocused.value
            )
            .onKeyEvent { event ->
                if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                    focusManager.clearFocus()
                    true
                } else {
                    false
                }
            }
            .clickable{ isFocused.value = true }
            .onFocusChanged { isFocused.value = it.isFocused },
        value = data.value.value,
        onValueChange = {
            data.value.error = null
            onChange(it)
        },
        placeholder = { Text(text = data.value.label) },
        singleLine = true,
        isError = data.value.error != null,
        shape = RoundedCornerShape(20.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            errorIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledIndicatorColor = androidx.compose.ui.graphics.Color.Transparent,
            disabledContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            focusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            unfocusedContainerColor = androidx.compose.ui.graphics.Color.Transparent,
            errorContainerColor = androidx.compose.ui.graphics.Color.Transparent
        ),
        trailingIcon = {
            if (trailingIcon != null) {
                IconButton(onTrailingIconClick) {
                    Icon(
                        imageVector = trailingIcon,
                        contentDescription = "action",
                        tint = if (data.value.error != null) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    )
}