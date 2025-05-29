package com.wiseowl.woli.ui.screen.detail.component

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wiseowl.woli.ui.event.Action
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperType
import com.wiseowl.woli.ui.screen.detail.DetailAction
import kotlin.reflect.KFunction1

@Composable
fun ChooserDialog(
    modifier: Modifier = Modifier,
    buttonColor: Color,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    onEvent: KFunction1<Action, Unit>
) {

    BackHandler {
        onEvent(DetailAction.OnDismissSetWallpaperDialog)
    }

    Dialog(
        onDismissRequest = { onEvent(DetailAction.OnDismissSetWallpaperDialog) }
    ) {
        Column(
            modifier = modifier.background(color = backgroundColor, shape = RoundedCornerShape(32.dp)),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(32.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Set for Home Screen", onClick = { onEvent(DetailAction.OnClickSetAs(SetWallpaperType.ONLY_HOME)) },
                backgroundColor = buttonColor,
                textColor = backgroundColor
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Set for Lock Screen", onClick = { onEvent(DetailAction.OnClickSetAs(SetWallpaperType.ONLY_LOCK)) },
                backgroundColor = buttonColor,
                textColor = backgroundColor
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Set for Both", onClick = { onEvent(DetailAction.OnClickSetAs(SetWallpaperType.FOR_BOTH)) },
                backgroundColor = buttonColor,
                textColor = backgroundColor
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Use other app", onClick = { onEvent(DetailAction.OnClickSetAs(SetWallpaperType.USE_OTHER_APP)) },
                backgroundColor = buttonColor,
                textColor = backgroundColor
            )
            Spacer(modifier = Modifier.size(32.dp))
        }
    }
}