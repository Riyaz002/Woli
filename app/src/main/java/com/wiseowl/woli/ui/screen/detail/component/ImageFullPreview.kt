package com.wiseowl.woli.ui.screen.detail.component

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.ui.screen.home.component.ImageCard

@Composable
fun ImageFullPreview(
    modifier: Modifier,
    image: Image,
    onDismiss: (() -> Unit)
) {
    val scale = remember { mutableFloatStateOf(1f) }
    val offset = remember { mutableStateOf(Offset(0f, 0f)) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnBackPress = true, usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = modifier.fillMaxSize()
        ) {
            ImageCard(
                modifier = modifier
                    .pointerInput(Unit) {
                        detectTransformGestures { _, pan, zoom, _ ->
                            // Update the scale based on zoom gestures.
                            scale.floatValue *= zoom

                            // Limit the zoom levels within a certain range (optional).
                            scale.floatValue = scale.floatValue.coerceIn(1f, 3f)

                            // Update the offset to implement panning when zoomed.
                            offset.value =
                                if (scale.floatValue == 1f) Offset(0f, 0f) else offset.value + pan
                        }
                    }
                    .graphicsLayer(
                        scaleX = scale.floatValue, scaleY = scale.floatValue,
                        translationX = offset.value.x, translationY = offset.value.y
                    ),
                image = image
            )
        }
    }
}