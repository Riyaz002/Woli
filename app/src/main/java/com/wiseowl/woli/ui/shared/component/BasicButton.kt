package com.wiseowl.woli.ui.shared.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.ui.shared.Constant
import com.wiseowl.woli.ui.shared.model.Button

@Preview
@Composable
fun BasicButton(
    modifier: Modifier = Modifier,
    data: Button = Button("Click Me"),
    onClick: () -> Unit = {}
) {
    androidx.compose.material3.Button(
        modifier = modifier,
        onClick = onClick,
        shape = RoundedCornerShape(Constant.DEFAULT_CORNER_RADIUS.dp)
    ) {
        Text(modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp), text = data.text)
    }
}
