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
import com.wiseowl.woli.domain.event.Action
import com.wiseowl.woli.domain.usecase.detail.SetWallpaperType
import com.wiseowl.woli.ui.screen.detail.DetailEvent
import kotlin.reflect.KFunction1

@Composable
fun ChooserDialog(
    modifier: Modifier = Modifier,
    buttonColor: Color,
    onEvent: KFunction1<Action, Unit>
) {

    BackHandler {
        onEvent(DetailEvent.OnDismissSetWallpaperDialog)
    }

    Dialog(
        onDismissRequest = { onEvent(DetailEvent.OnDismissSetWallpaperDialog) }
    ) {
        Column(
            modifier = modifier.background(color = MaterialTheme.colorScheme.background, shape = RoundedCornerShape(32.dp)),
            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Set for Home Screen", onClick = { onEvent(DetailEvent.OnClickSetAs(SetWallpaperType.ONLY_HOME)) },
                color = buttonColor
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Set for Lock Screen", onClick = { onEvent(DetailEvent.OnClickSetAs(SetWallpaperType.ONLY_LOCK)) },
                color = buttonColor
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Set for Both", onClick = { onEvent(DetailEvent.OnClickSetAs(SetWallpaperType.FOR_BOTH)) },
                color = buttonColor
            )
            Spacer(modifier = Modifier.size(10.dp))
            TextRoundButton(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp), text = "Use other app", onClick = { onEvent(DetailEvent.OnClickSetAs(SetWallpaperType.USE_OTHER_APP)) },
                color = buttonColor
            )
            Spacer(modifier = Modifier.size(10.dp))
        }
    }
}