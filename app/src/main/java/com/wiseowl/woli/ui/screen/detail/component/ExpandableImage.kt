package com.wiseowl.woli.ui.screen.detail.component

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.wiseowl.woli.domain.event.Event
import com.wiseowl.woli.domain.model.Image
import com.wiseowl.woli.ui.screen.detail.DetailEvent
import com.wiseowl.woli.ui.screen.home.component.ImageCard
import kotlin.reflect.KFunction1

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun ExpandableImageCard(
    modifier: Modifier,
    image: Image,
    expanded: Boolean = false,
    onDismiss: (() -> Unit),
    onClick: KFunction1<Event, Unit>
) {
    val scale = remember { mutableFloatStateOf(1f) }
    val offset = remember { mutableStateOf(Offset(0f, 0f)) }

    BackHandler {
        onDismiss()
    }
    SharedTransitionLayout {
        AnimatedContent(
            targetState = expanded,
            label = "basic"
        ) { targ ->
            if (!targ) {
                with(this@SharedTransitionLayout) {
                    ImageCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp)
                            .sharedBounds(
                                rememberSharedContentState(key = "bounds"),
                                exit = fadeOut(),
                                enter = fadeIn(),
                                animatedVisibilityScope = this@AnimatedContent,
                                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                            ),
                        image = image,
                        cornerRadius = 20.dp,
                        aspectRatio = 1f,
                        onClick = { onClick(DetailEvent.OnClickImage) }
                    )
                }
            } else {
                with(this@SharedTransitionLayout) {
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
                            ).fillMaxWidth()
                            .sharedBounds(
                                rememberSharedContentState(key = "bounds"),
                                enter = fadeIn(),
                                exit = fadeOut(),
                                animatedVisibilityScope = this@AnimatedContent,
                                resizeMode = SharedTransitionScope.ResizeMode.ScaleToBounds()
                            ),
                        image = image,
                    )
                }
            }
        }
    }
}